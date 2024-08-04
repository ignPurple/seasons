package net.ignpurple.seasons.api.config.data;

import net.ignpurple.seasons.api.config.ConfigManager;
import net.ignpurple.seasons.api.config.annotation.Config;
import org.bukkit.configuration.Configuration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;

public class ConfigSerializerContext {
    private final ConfigManager configManager;
    private final Config configAnnotation;
    private final Configuration yamlConfiguration;
    private final Field field;

    public ConfigSerializerContext(ConfigManager configManager, Config configAnnotation, Configuration yamlConfiguration, Field field) {
        this.configManager = configManager;
        this.configAnnotation = configAnnotation;
        this.yamlConfiguration = yamlConfiguration;
        this.field = field;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public Config getConfigAnnotation() {
        return this.configAnnotation;
    }

    public Configuration getYamlConfiguration() {
        return this.yamlConfiguration;
    }

    public Field getField() {
        return this.field;
    }

    public <T> T deserialize(Class<T> objectClass, String path, String key) {
        try {
            if (Number.class.isAssignableFrom(objectClass)) {
                return ConfigManager.GSON.getAdapter(objectClass).fromJson(key);
            }

            if (String.class.isAssignableFrom(objectClass)) {
                return (T) key;
            }

            if (Enum.class.isAssignableFrom(objectClass)) {
                return (T) Enum.valueOf((Class<? extends Enum>) objectClass, key.toUpperCase(Locale.ROOT));
            }

            return this.configManager.deserialize(this, objectClass, path, key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
