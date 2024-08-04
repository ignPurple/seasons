package net.ignpurple.seasons.config.hydration.effect;

import net.ignpurple.seasons.api.config.annotation.Section;

@Section
public class HydrationEffectSection {
    private int effectAmplifier = 0;
    private float hydrationPercentageActivation = 50f;

    public int getEffectAmplifier() {
        return this.effectAmplifier;
    }

    public float getHydrationPercentageActivation() {
        return this.hydrationPercentageActivation;
    }
}
