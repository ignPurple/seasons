package net.ignpurple.seasons.config.temperature;

import net.ignpurple.seasons.api.config.annotation.Section;

@Section
public class TemperatureSection {
    private boolean enabled = false;
    private float maxTemperature = 22.5f;
    private float minTemperature = -2.5f;

    public boolean isEnabled() {
        return this.enabled;
    }

    public float getMaxTemperature() {
        return this.maxTemperature;
    }

    public float getMinTemperature() {
        return this.minTemperature;
    }
}
