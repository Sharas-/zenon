package com.pulloware.zenon.domain;

import java.text.MessageFormat;

/**
 * Maps mindfulness level to alert interval.
 */
public class AlertInterval
{
    private static int[][] levelToTimeMap = new int[][]
        {
            {20, 40},
            {30, 60},
            {2 * 60, 10 * 60},
            {20 * 60, 45 * 60},
            {60 * 60, 150 * 60},
            {180 * 60, 360 * 60},
        };

    public static final int minLevel = 0;
    public static final int maxLevel = 5;
    private int level;

    public AlertInterval(int mindfulnessLevel)
    {
        if (mindfulnessLevel < minLevel || mindfulnessLevel > maxLevel)
        {
            throw new IllegalArgumentException(
                MessageFormat.format("Mindfulness level must be within [{0}, {1}]", minLevel, maxLevel));
        }
        this.level = mindfulnessLevel;
    }


    public int from()
    {
        return levelToTimeMap[level][0];
    }

    public int to()
    {
        return levelToTimeMap[level][1];
    }
}
