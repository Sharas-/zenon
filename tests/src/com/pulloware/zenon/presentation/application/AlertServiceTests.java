package com.pulloware.zenon.presentation.application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.test.AndroidTestCase;
import com.pulloware.zenon.application.AlertPlayer;
import com.pulloware.zenon.application.commands.PlayAlert;
import com.pulloware.zenon.application.commands.StopAlerts;
import com.pulloware.zenon.infrastructure.Trace;

/**
 * Created by sharas on 7/27/15.
 */
public class AlertServiceTests extends AndroidTestCase
{
    public void testAlertPlaysWhenScheduled() throws Exception
    {
        Trace.messages().clear();
        Trace.on = true;
        scheduleAlarmCommand(20, new PlayAlert(this.getContext()));
        Thread.sleep(40);
        int plays = Trace.countTopic(AlertPlayer.class.getSimpleName());
        assertEquals(1, plays);
    }

    public void testOnlyOneAlertPlaysWhenRescheduled() throws Exception
    {
        Trace.messages().clear();
        Trace.on = true;
        scheduleAlarmCommand(20, new PlayAlert(this.getContext()));
        scheduleAlarmCommand(40, new PlayAlert(this.getContext()));
        Thread.sleep(80);
        int plays = Trace.countTopic(AlertPlayer.class.getSimpleName());
        assertEquals(1, plays);
    }

    public void testAlertDoesntPlayWhenStopped() throws Exception
    {
        Trace.messages().clear();
        Trace.on = true;
        scheduleAlarmCommand(20, new PlayAlert(this.getContext()));
        scheduleAlarmCommand(40, new StopAlerts(this.getContext()));
        Thread.sleep(80);
        int plays = Trace.countTopic(AlertPlayer.class.getSimpleName());
        assertEquals(1, plays);
    }

    private void scheduleAlarmCommand(long msAfter, Intent i)
    {
        Context c = getContext();
        long when = SystemClock.elapsedRealtime() + msAfter;
        PendingIntent pendingIntent = PendingIntent.getService(c, 1,
            i, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, when, pendingIntent);
    }
}
