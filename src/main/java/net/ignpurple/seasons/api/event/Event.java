package net.ignpurple.seasons.api.event;

import org.bukkit.entity.Player;

public abstract class Event {
    private final Player player;

    public Event(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
