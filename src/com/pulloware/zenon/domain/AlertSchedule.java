package com.pulloware.zenon.domain;

import java.util.Iterator;

/**
 * Created by sharas on 7/15/15.
 */
public class AlertSchedule implements Iterator<Integer>
{
    private int[] intervals;
    private int idx = 0;

    public AlertSchedule(AlertInterval interval)
    {
        intervals = new int[]{3, 10, 5};
    }

    @Override
    public boolean hasNext()
    {
        return true;
    }

    @Override
    public Integer next()
    {
        idx = idx % intervals.length;
        return intervals[idx++];
    }

    @Override
    public void remove()
    {

    }
}
