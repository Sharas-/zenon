package com.pulloware.zenon.domain;

import junit.framework.TestCase;

import java.text.MessageFormat;

/**
 * Created by sharas on 7/31/15.
 */
public class AlertTimeTests extends TestCase
{
    public void testAlertTimesAreWithinIntervals()
    {
        for (int level = AlertTime.minMindfulnessLevel; level <= AlertTime.maxMindfulnessLevel; ++level)
        {
            int time = AlertTime.next(level);
            int lower = AlertTime.intervals[level][0];
            int upper = AlertTime.intervals[level][1];
            assertTrue(MessageFormat.format("time {0} for level {1} is out of [{2}, {3}] range", time, level, lower, upper),
                time >= lower && time <= upper);
        }
    }
}
