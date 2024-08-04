package net.ignpurple.seasons.api.listener;

import net.ignpurple.seasons.api.event.TemperatureEvent;

public interface TemperatureListener extends EventListener<TemperatureEvent> {

    @Override
    default void handleEvent(TemperatureEvent event) {
        if (event == null) {
            return;
        }

        this.temperatureEvent((TemperatureEvent) event);
    }

    void temperatureEvent(TemperatureEvent event);
}
