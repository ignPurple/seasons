package net.ignpurple.seasons.api.config.serializer;

import net.ignpurple.seasons.api.config.data.ConfigSerializerContext;
import org.bukkit.configuration.ConfigurationSection;

public interface Serializer<T> {
    default void serialize(ConfigSerializerContext configSerializerContext, String path, Object defaultObject) {}

    default T deserialize(ConfigSerializerContext configSerializerContext, ConfigurationSection section, Object defaultObject) {
        return (T) defaultObject;
    }
}
