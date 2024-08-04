package net.ignpurple.seasons.task;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.ignpurple.seasons.Seasons;
import net.ignpurple.seasons.config.SeasonsConfig;
import net.ignpurple.seasons.config.timescale.TimeScaleSection;
import net.ignpurple.seasons.util.constant.Constants;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TimeTask implements Runnable {
    private final TimeScaleSection timeScaleSection;
    private final Cache<String, Double> worldTimeCache = CacheBuilder.newBuilder()
        .expireAfterWrite(30, TimeUnit.SECONDS)
        .build();

    public TimeTask(Seasons plugin) {
        this.timeScaleSection = plugin.getConfig(SeasonsConfig.class).getTimeScale();
        Bukkit.getScheduler().runTaskTimer(plugin, this, 1, 1);
    }

    @Override
    public void run() {
        if (!this.timeScaleSection.isEnabled()) {
            return;
        }

        try {
            for (final World world : Bukkit.getWorlds()) {
                final double currentTime = this.worldTimeCache.get(world.getName(), () -> (double) world.getFullTime());
                world.setFullTime((long) currentTime);
                this.worldTimeCache.put(world.getName(), currentTime + ((Constants.TICKS_PER_INGAME_MINUTE / this.timeScaleSection.getTicksPerIngameMinute())));
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
