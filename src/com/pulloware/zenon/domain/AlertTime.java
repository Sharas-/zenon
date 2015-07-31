package com.pulloware.zenon.domain;

import java.text.MessageFormat;
import java.util.Random;

/**
 * Maps mindfulness level to alert time.
 */
public class AlertTime
{
    public static int[][] intervals = new int[][]
        {
            {20, 40},
            {30, 60},
            {2 * 60, 10 * 60},
            {20 * 60, 45 * 60},
            {60 * 60, 150 * 60},
            {180 * 60, 360 * 60},
        };

    public static final int minMindfulnessLevel = 0;
    public static final int maxMindfulnessLevel = 5;

    public static void throwIfLevelInvalid(int mindfulnessLevel)
    {
        if (mindfulnessLevel < minMindfulnessLevel || mindfulnessLevel > maxMindfulnessLevel)
        {
            throw new IllegalArgumentException(
                MessageFormat.format("Mindfulness level must be within [{0}, {1}]", minMindfulnessLevel, maxMindfulnessLevel));
        }
    }

    public static int next(int mindfulnessLevel)
    {
        throwIfLevelInvalid(mindfulnessLevel);
        int lower = intervals[mindfulnessLevel][0];
        int upper = intervals[mindfulnessLevel][1];
        return new Random().nextInt((upper - lower) + 1) + lower;
    }
}
