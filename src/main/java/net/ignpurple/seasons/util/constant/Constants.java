package net.ignpurple.seasons.util.constant;

import org.bukkit.NamespacedKey;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Constants {
    public static float TICKS_PER_INGAME_MINUTE = 16.6666666666f;
    public static float TIME_TO_SUN_ANGLE = (2f/3f) * 100f;
    public static final NumberFormat NUMBER_FORMAT = new DecimalFormat("0.00");

    // Persistent Keys
    public static final NamespacedKey HYDRATION_KEY = NamespacedKey.fromString("seasons:hydration");
}
