package net.ignpurple.seasons.api.event;

import org.bukkit.entity.Player;

public class TemperatureEvent extends Event {
    private final float temperature;

    public TemperatureEvent(Player player, float temperature) {
        super(player);
        this.temperature = temperature;
    }

    public float getTemperature() {
        return this.temperature;
    }
}
