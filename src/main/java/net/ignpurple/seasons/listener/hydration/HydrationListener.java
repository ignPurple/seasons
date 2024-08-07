package net.ignpurple.seasons.listener.hydration;

import net.ignpurple.seasons.Seasons;
import net.ignpurple.seasons.api.event.TemperatureEvent;
import net.ignpurple.seasons.api.listener.TemperatureListener;
import net.ignpurple.seasons.config.SeasonsConfig;
import net.ignpurple.seasons.config.hydration.HydrationSection;
import net.ignpurple.seasons.config.hydration.effect.HydrationEffectSection;
import net.ignpurple.seasons.util.adventure.ComponentUtil;
import net.ignpurple.seasons.util.constant.Constants;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class HydrationListener implements TemperatureListener {
    private final HydrationSection hydration;

    public HydrationListener(Seasons plugin) {
        final SeasonsConfig seasonsConfig = plugin.getConfig(SeasonsConfig.class);
        this.hydration = seasonsConfig.getHydration();
    }

    @Override
    public void temperatureEvent(TemperatureEvent event) {
        if (!this.hydration.isEnabled()) {
            return;
        }

        final Player player = event.getPlayer();
        final float temperature = event.getTemperature();
        final float maxHydration = this.hydration.getMaxHydration();
        final PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
        final float minTemperature = Math.max(0.1f, this.hydration.getMinHydrationTemperatureEffect());
        final float hydrationChange = Math.max(minTemperature, temperature) * this.hydration.getHydrationTemperatureMultiplierPerTick();
        final float hydration = Math.max(0, persistentDataContainer.getOrDefault(Constants.HYDRATION_KEY, PersistentDataType.FLOAT, maxHydration) - hydrationChange);
        for (final Map.Entry<PotionEffectType, HydrationEffectSection> effectsEntry : this.hydration.getEffects().entrySet()) {
            final PotionEffectType potionType = effectsEntry.getKey();
            final HydrationEffectSection hydrationEffect = effectsEntry.getValue();
            if (hydration > hydrationEffect.getHydrationPercentageActivation()) {
                continue;
            }

            player.addPotionEffect(new PotionEffect(potionType, -1, hydrationEffect.getEffectAmplifier(), true, false, false));
        }

        persistentDataContainer.set(Constants.HYDRATION_KEY, PersistentDataType.FLOAT, hydration);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, ComponentUtil.serializeMiniStringToTextComponent("<rainbow>Hydration: " + Constants.NUMBER_FORMAT.format(hydration) + "%" + " Temperature: " + Constants.NUMBER_FORMAT.format(temperature) + "°C"));
        //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Temperature: " + Constants.NUMBER_FORMAT.format(temperature) + "°C"));
    }
}
