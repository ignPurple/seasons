package net.ignpurple.seasons.task;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.ignpurple.seasons.Seasons;
import net.ignpurple.seasons.api.event.TemperatureEvent;
import net.ignpurple.seasons.api.event.handler.EventBus;
import net.ignpurple.seasons.config.SeasonsConfig;
import net.ignpurple.seasons.config.temperature.TemperatureSection;
import net.ignpurple.seasons.config.timescale.TimeScaleSection;
import net.ignpurple.seasons.util.constant.Constants;
import net.ignpurple.seasons.util.temperature.TemperatureUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TemperatureTask implements Runnable {
    private final EventBus eventBus;
    private final TemperatureSection temperature;
    private final TimeScaleSection timeScale;
    private final Cache<String, Long> worldTimeCache = CacheBuilder.newBuilder()
        .expireAfterWrite(30, TimeUnit.SECONDS)
        .build();

    public TemperatureTask(Seasons plugin) {
        this.eventBus = plugin.getEventBus();
        this.temperature = plugin.getConfig(SeasonsConfig.class).getTemperature();
        this.timeScale = plugin.getConfig(SeasonsConfig.class).getTimeScale();
        Bukkit.getScheduler().runTaskTimer(plugin, this, 1, 1);
    }

    @Override
    public void run() {
        if (!this.temperature.isEnabled()) {
            return;
        }

        try {
            final World world = Bukkit.getWorld("world");
            final long time = world.getTime();
            final long cachedTime = this.worldTimeCache.get(world.getName(), () -> time);
            if (cachedTime == time) {
                return;
            }

            final float temperature = TemperatureUtil.getTemperature(time, this.temperature.getMinTemperature(), this.temperature.getMaxTemperature());
            final float temperatureWithTimeScale = (float) (this.timeScale.isEnabled() ? temperature * (Constants.TICKS_PER_INGAME_MINUTE / this.timeScale.getTicksPerIngameMinute()) : temperature);
            for (final Player player : Bukkit.getOnlinePlayers()) {
                this.eventBus.executeEvent(new TemperatureEvent(player, temperature));
            }
            this.worldTimeCache.put(world.getName(), time);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
