package net.ignpurple.seasons.util.event;

import net.ignpurple.seasons.Seasons;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.function.Consumer;

public class Events {

    public static <T extends Event> void register(Class<T> eventClass, Consumer<T> eventConsumer) {
        final ListenerHolder holder = (listener, event) -> eventConsumer.accept((T) event);

        Bukkit.getPluginManager().registerEvent(
            eventClass,
            holder, // test
            EventPriority.NORMAL,
            holder,
            Seasons.getPlugin(Seasons.class)
        );
    }
}
