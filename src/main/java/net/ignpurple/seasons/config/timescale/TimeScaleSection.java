package net.ignpurple.seasons.config.timescale;

import net.ignpurple.seasons.api.config.annotation.Section;

@Section
public class TimeScaleSection {
    private boolean enabled = false;
    private int ticksPerIngameMinute = 20;

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getTicksPerIngameMinute() {
        return this.ticksPerIngameMinute;
    }
}
