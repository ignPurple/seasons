package net.ignpurple.seasons.api.listener;

import net.ignpurple.seasons.api.event.Event;

public interface EventListener<T extends Event> {
    void handleEvent(T event);
}
