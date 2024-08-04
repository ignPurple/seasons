package net.ignpurple.seasons.api.event.handler;

import net.ignpurple.seasons.api.event.Event;
import net.ignpurple.seasons.api.listener.EventListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class EventBus {
    private final Logger logger;
    private final Map<Class<? extends Event>, Set<EventListener>> eventListeners;

    public EventBus(JavaPlugin plugin) {
        this.logger = plugin.getLogger();
        this.eventListeners = new HashMap<>();
    }

    public void executeEvent(Event event) {
        final Set<EventListener> eventListeners = this.eventListeners.get(event.getClass());
        if (eventListeners == null) {
            return;
        }

        eventListeners.forEach((eventListener) -> eventListener.handleEvent(event));
    }

    public <T extends EventListener<?>> void register(T eventListener) {
        for (final Class<?> interfaces : eventListener.getClass().getInterfaces()) {
            if (!EventListener.class.isAssignableFrom(interfaces)) {
                continue;
            }

            if (interfaces.equals(EventListener.class)) {
                this.logger.warning("Do not directly implement the EventListener class inside of " + eventListener.getClass() + ", instead use the defined Listeners in net.ignpurple.seasons.api.listener");
                continue;
            }

            final Class<?> eventClass = (Class<?>) ((ParameterizedType) interfaces.getGenericInterfaces()[0]).getActualTypeArguments()[0];
            if (!Event.class.isAssignableFrom(eventClass)) {
                continue;
            }

            this.eventListeners.computeIfAbsent((Class<? extends Event>) eventClass, ($) -> new HashSet<>()).add(eventListener);
        }
    }
}
