package net.ignpurple.seasons.config;

import net.ignpurple.seasons.api.config.annotation.Config;
import net.ignpurple.seasons.api.config.annotation.FieldVersion;
import net.ignpurple.seasons.config.hydration.HydrationSection;
import net.ignpurple.seasons.config.temperature.TemperatureSection;
import net.ignpurple.seasons.config.timescale.TimeScaleSection;

@Config(file = "config.yml", version = 1)
public class SeasonsConfig {
    @FieldVersion(1)
    private final TimeScaleSection timeScale = new TimeScaleSection();

    @FieldVersion(1)
    private final TemperatureSection temperature = new TemperatureSection();

    @FieldVersion(1)
    private final HydrationSection hydration = new HydrationSection();

    public TimeScaleSection getTimeScale() {
        return this.timeScale;
    }

    public TemperatureSection getTemperature() {
        return this.temperature;
    }

    public HydrationSection getHydration() {
        return this.hydration;
    }
}
