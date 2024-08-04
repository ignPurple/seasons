package net.ignpurple.seasons.serializer;

import net.ignpurple.seasons.api.config.data.ConfigSerializerContext;
import net.ignpurple.seasons.api.config.serializer.Serializer;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectTypeSerializer implements Serializer<PotionEffectType> {

    @Override
    public PotionEffectType deserialize(ConfigSerializerContext configSerializerContext, ConfigurationSection section, Object defaultObject) {
        return Registry.EFFECT.get(NamespacedKey.fromString((String) defaultObject));
    }
}
