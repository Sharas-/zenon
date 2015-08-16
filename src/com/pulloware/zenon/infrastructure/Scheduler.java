package com.pulloware.zenon.infrastructure;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by sharas on 7/28/15.
 */
public class Scheduler
{
    public static void schedule(Intent command, long afterMs, Context c)
    {
        long time = SystemClock.elapsedRealtime() + afterMs;
            ((AlarmManager) c.getSystemService(Context.ALARM_SERVICE)).set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP, time, makePendingIntent(command, c));
    }

    public static void cancel(Intent command, Context c)
    {
        ((AlarmManager) c.getSystemService(Context.ALARM_SERVICE)).cancel(
            makePendingIntent(command, c));
    }

    private static PendingIntent makePendingIntent(Intent command, Context c)
    {
        return PendingIntent.getService(c, 1, command, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
