package com.pulloware.zenon.domain;

import java.text.DecimalFormat;
import java.text.MessageFormat;

/**
 * Created by sharas on 8/2/15.
 */
public class TimeInterval
{
    public int lower;
    public int upper;

    private static final DecimalFormat formatter = new DecimalFormat("0.#");

    public TimeInterval(int lowerS, int upperS)
    {
        if (upperS <= lowerS)
        {
            throw new IllegalArgumentException("Upper bound of interval must be greater than lower bound");
        }
        this.lower = lowerS;
        this.upper = upperS;
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("[{0} - {1}]", toHumanReadable(lower), toHumanReadable(upper));
    }

    private String toHumanReadable(int seconds)
    {
        if (seconds / 3600 > 0)
        {
            return formatter.format(seconds / 3600F) + "h";
        }
        if (seconds / 60 > 0)
        {
            return formatter.format(seconds / 60F) + "min";
        }
        return seconds + "s";
    }
}
