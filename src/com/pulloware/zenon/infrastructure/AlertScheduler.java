package com.pulloware.zenon.infrastructure;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Log;

import java.util.Iterator;

/**
 * Created by sharas on 7/11/15.
 */
public class AlertScheduler extends BroadcastReceiver
{
    private final AlertPlayer player;
    private Iterator<Integer> intervals;
    private AlarmManager alarmManager;
    private PendingIntent broadcastIntent;
    private Context context;

    public AlertScheduler(Context c, AlertPlayer player, Iterator<Integer> intervals)
    {
        if (c == null)
        {
            throw new IllegalArgumentException("Cannot create Alert Scheduler without context");
        }
        if (intervals == null || !intervals.hasNext())
        {
            throw new IllegalArgumentException("Cannot create Alert Scheduler without schedule intervals");
        }
        if (player == null)
        {
            throw new IllegalArgumentException("Cannot create Alert Scheduler without Alert Player");
        }
        this.intervals = intervals;
        this.player = player;
        String actionId = this.toString();
        broadcastIntent = PendingIntent.getBroadcast(c, 1, new Intent(actionId), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        this.context = c;
        context.registerReceiver(this, new IntentFilter(actionId));
        scheduleAlarm(intervals.next());
    }

    public void dispose()
    {
        broadcastIntent.cancel();
        alarmManager.cancel(broadcastIntent);
        context.unregisterReceiver(this);
    }

    private void scheduleAlarm(int at)
    {
        int atMs = at * 1000;
        Log.d("alarm", "scheduling after: " + atMs);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + atMs, broadcastIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("alarm", "Playing now in thread :" + Thread.currentThread().getId());
        try
        {
            player.play();
            if (intervals.hasNext())
            {
                scheduleAlarm(intervals.next());
            } else
            {
                context.unregisterReceiver(this);
            }
        } catch (Exception e)
        {
            Log.e(this.getClass().getSimpleName(), e.getMessage(), e);
        }
    }
}
