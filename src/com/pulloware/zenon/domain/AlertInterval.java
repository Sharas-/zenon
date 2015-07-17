package com.pulloware.zenon.domain;

import java.text.MessageFormat;

/**
 * Maps mindfulness level to alert interval.
 * Upper and lower bounds grow exponentially with level.
 */
public class AlertInterval
{
    private static final short timeUnit = 60; // to map level to seconds
    private static final float upperGrowthFactor = 2.5F; // faster growth adds more randomness to intervals, especially for high levels
    private static final float lowerGrowthFactor = 1.2F; // slower growth minimizes wait times for low levels making them less "weighty"
    private static final int lowerOffset = 1; // constant added to lower bound to avoid getting zero for low levels
    private static final int upperOffset = 2; // constant added to upper bound to keep it above lower bound for low levels

    public static final int minLevel = 1;
    public static final int maxLevel = 100;

    private int lower, upper;

    public AlertInterval(int level)
    {
        if (level < minLevel || level > maxLevel)
        {
            throw new IllegalArgumentException(
                MessageFormat.format("Mindfulness level must be within [{0}, {1}]", minLevel, maxLevel));
        }
        upper = levelToTime(level, lowerOffset, lowerGrowthFactor);
        lower = levelToTime(level, upperOffset, upperGrowthFactor);
    }

    private static int levelToTime(int level, int offset, float growthFactor)
    {
        return (int) (offset + level * level / maxLevel * growthFactor * timeUnit);
    }

    public int from()
    {
        return lower;
    }

    public int to()
    {
        return upper;
    }
}
