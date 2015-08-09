package com.pulloware.zenon.domain;

import java.text.MessageFormat;
import java.util.Random;

/**
 * Maps mindfulness level to alert time.
 */
public class AlertTime
{
    private static TimeInterval[] intervals = new TimeInterval[]
        {
            new TimeInterval(20, 40),
            new TimeInterval(30, 60),
            new TimeInterval(2 * 60, 10 * 60),
            new TimeInterval(20 * 60, 45 * 60),
            new TimeInterval(60 * 60, 150 * 60),
        };

    public static final int levelCount = 5;

    public static void throwIfLevelInvalid(int mindfulnessLevel)
    {
        if (mindfulnessLevel < 0 || mindfulnessLevel > levelCount)
        {
            throw new IllegalArgumentException(
                MessageFormat.format("Mindfulness level must be within [{0}, {1}]", 0, levelCount));
        }
    }

    public static int next(int level)
    {
        throwIfLevelInvalid(level);
        TimeInterval t = intervals[level];
        return new Random().nextInt((t.upper - t.lower) + 1) + t.lower;
    }

    public static TimeInterval getInterval(int level)
    {
        throwIfLevelInvalid(level);
        return intervals[level];
    }
}
