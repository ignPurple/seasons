package net.ignpurple.seasons.config.hydration;

import net.ignpurple.seasons.api.config.annotation.Section;
import net.ignpurple.seasons.config.hydration.effect.HydrationEffectSection;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

@Section
public class HydrationSection {
    private boolean enabled = false;
    private float minHydrationTemperatureEffect = 1.25f;
    private float hydrationTemperatureMultiplierPerTick = 0.000225f;
    private float maxHydration = 100f;

    private Map<PotionEffectType, HydrationEffectSection> effects = new HashMap<>();

    public HydrationSection() {
        this.effects.put(PotionEffectType.POISON, new HydrationEffectSection());
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public float getMinHydrationTemperatureEffect() {
        return this.minHydrationTemperatureEffect;
    }

    public float getHydrationTemperatureMultiplierPerTick() {
        return this.hydrationTemperatureMultiplierPerTick;
    }

    public float getMaxHydration() {
        return this.maxHydration;
    }

    public Map<PotionEffectType, HydrationEffectSection> getEffects() {
        return this.effects;
    }
}
