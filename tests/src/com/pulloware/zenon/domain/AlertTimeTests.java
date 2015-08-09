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
        for (int level = 0; level < AlertTime.levelCount; ++level)
        {
            int time = AlertTime.next(level);
            TimeInterval t = AlertTime.getInterval(level);
            assertTrue(MessageFormat.format("time {0} for level {1} is out of [{2}, {3}] range", time, level, t.lower, t.upper),
                time >= t.lower && time <= t.upper);
        }
    }
}
