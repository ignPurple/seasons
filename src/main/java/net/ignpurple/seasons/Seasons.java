package net.ignpurple.seasons;

import net.ignpurple.seasons.api.config.ConfigManager;
import net.ignpurple.seasons.api.event.handler.EventBus;
import net.ignpurple.seasons.config.SeasonsConfig;
import net.ignpurple.seasons.listener.hydration.HydrationListener;
import net.ignpurple.seasons.serializer.PotionEffectTypeSerializer;
import net.ignpurple.seasons.task.TemperatureTask;
import net.ignpurple.seasons.task.TimeTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class Seasons extends JavaPlugin {
    private ConfigManager configManager;
    private EventBus eventBus;

    public void onEnable() {
        this.initConfig();
        this.initEventBus();
        this.initListeners();
        this.initTasks();
    }

    public <T> T getConfig(Class<T> configClass) {
        return this.configManager.getConfig(configClass);
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    private void initConfig() {
        this.configManager = new ConfigManager(this);
        this.initSerializers();

        this.configManager.loadConfig(SeasonsConfig.class, SeasonsConfig::new);
    }

    private void initSerializers() {
        this.configManager.registerSerializer(PotionEffectType.class, new PotionEffectTypeSerializer());
    }

    private void initEventBus() {
        this.eventBus = new EventBus(this);
    }

    private void initListeners() {
        this.eventBus.register(new HydrationListener(this));
    }

    private void initTasks() {
        new TimeTask(this);
        new TemperatureTask(this);
    }
}
