package net.ignpurple.seasons.util.temperature;

import net.ignpurple.seasons.util.constant.Constants;

public final class TemperatureUtil {

    private TemperatureUtil() {}

    public static float getTemperature(long time, double minTemperature, double maxTemperature) {
        final double sunAngle = (time % 24000) / Constants.TIME_TO_SUN_ANGLE;

        // Difference between the minimum and maximum temperature
        final double minMaxDelta = maxTemperature - minTemperature;

        // Convert sin range from -1 -> 1 to 0, 2
        // Divide sin range by 2 (0, 2 -> 0, 1)
        // Multiply that range by the amplitude so your range becomes 0 -> amplitude
        // Add min to range, so it becomes min + (0 -> amplitude)
        return (float) ((((Math.sin(Math.toRadians(sunAngle)) + 1) / 2d) * minMaxDelta) + minTemperature);
    }
}
