package com.pulloware.zenon.infrastructure;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sharas on 7/27/15.
 */
public class Trace
{
    public static boolean on = false;
    private static Collection<String> messages = new ArrayList<String>();

    public static void post(String topic, String message)
    {
        messages.add(topic + ": " + message);
    }

    public static Collection<String> messages()
    {
        return messages;
    }

    public static int countTopic(String topic)
    {
        int cnt = 0;
        for (String m : messages)
        {
            if (m.startsWith(topic))
                ++cnt;
        }
        return cnt;
    }
}
