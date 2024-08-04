package net.ignpurple.seasons.api.config.serializer.map;

import net.ignpurple.seasons.api.config.ConfigManager;
import net.ignpurple.seasons.api.config.annotation.Config;
import net.ignpurple.seasons.api.config.data.ConfigSerializerContext;
import net.ignpurple.seasons.api.config.serializer.Serializer;
import org.bukkit.Keyed;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class MapSerializer implements Serializer<Map<Object, Object>> {

    @Override
    public void serialize(ConfigSerializerContext configSerializerContext, String path, Object defaultObject) {
        final ConfigManager configManager = configSerializerContext.getConfigManager();
        final Config configAnnotation = configSerializerContext.getConfigAnnotation();
        final Configuration configuration = configSerializerContext.getYamlConfiguration();

        final Map<Object, Object> map = (Map<Object, Object>) defaultObject;
        for (final Map.Entry<Object, Object> entry : map.entrySet()) {
            final Object key = entry.getKey();
            if (!this.isSerializableKey(key)) {
                throw new IllegalArgumentException("Cannot serialize map to " + path + " due to unserializable key " + key.getClass().getSimpleName());
            }

            final String keyString = this.keyToString(key);
            final Object value = entry.getValue();
            if (this.isDefaultSerializableValue(value)) {
                configuration.set(path + "." + keyString, value);
                return;
            }

            configManager.scanConfig(value.getClass(), value, configAnnotation, configuration, path + "." + keyString + ".");
        }
    }

    @Override
    public Map<Object, Object> deserialize(ConfigSerializerContext configSerializerContext, ConfigurationSection section, Object defaultObject) {
        try {
            final ConfigManager configManager = configSerializerContext.getConfigManager();
            final Config configAnnotation = configSerializerContext.getConfigAnnotation();
            final Configuration configuration = configSerializerContext.getYamlConfiguration();
            final Field mapField = configSerializerContext.getField();
            final Map<Object, Object> map = new HashMap<>();

            final ParameterizedType mapType = (ParameterizedType) mapField.getGenericType();
            final Class<?> key = (Class<?>) mapType.getActualTypeArguments()[0];
            final Class<?> value = (Class<?>) mapType.getActualTypeArguments()[1];
            final String path = section.getCurrentPath();
            for (final String keys : section.getKeys(false)) {
                final Object valueObject = value.getDeclaredConstructor().newInstance();
                configManager.scanConfig(value, valueObject, configAnnotation, configuration, path + "." + keys);

                map.put(configSerializerContext.deserialize(key, path, keys), valueObject);
            }

            return map;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isSerializableKey(Object key) {
        return key instanceof String || key instanceof Number || key instanceof Enum<?> || key instanceof Keyed;
    }

    private String keyToString(Object key) {
        if (key instanceof Serializable) {
            return key.toString();
        }

        if (key instanceof Keyed) {
            return ((Keyed) key).getKey().getKey();
        }

        throw new IllegalArgumentException("How did we get here?");
    }

    private boolean isDefaultSerializableValue(Object value) {
        return value instanceof Serializable;
    }

    private void serializeMap(Configuration configuration, Field mapField) {

    }
}
