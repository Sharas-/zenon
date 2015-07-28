package com.pulloware.zenon.application;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.pulloware.zenon.application.commands.PlayAlert;
import com.pulloware.zenon.domain.AlertTime;

/**
 * Created by sharas on 7/14/15.
 */
public class AlertService extends IntentService
{
    public static final String CMD_PLAY_ALERT = "CMD_PLAY_ALERT";
    public static final String CMD_START_ALERTS = "CMD_START_ALERTS";
    public static final String CMD_STOP_ALERTS = "CMD_STOP_ALERTS";
    public static final String PARAM_MINDFULNESS_LEVEL = "PARAM_MINDFULNESS_LEVEL";

    public AlertService()
    {
        super(AlertService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        String command = intent.getAction();
        if (command == CMD_PLAY_ALERT)
        {
//            Log.d("AlertService", "playing now");
            new AlertPlayer(this).playAsync();
            if (intent.hasExtra(PARAM_MINDFULNESS_LEVEL))
            {
                ScheduleAlert(getLevel(intent));
            }
        }
        else if (command == CMD_START_ALERTS)
        {
            ScheduleAlert(getLevel(intent));
        }
        else if (command == CMD_STOP_ALERTS)
        {
            cancelAlert();
        }
        else
        {
            throw new IllegalArgumentException("Don't know how to handle command " + intent.getAction());
        }
    }

    private void ScheduleAlert(int level)
    {
        long nextAlertTimeMs = SystemClock.elapsedRealtime() + AlertTime.next(level) * 1000;
//        Log.d("AlertService", "scheduling after " + nextAlertTimeMs);
        PlayAlert cmdPlayAlert = new PlayAlert(this);
        cmdPlayAlert.putExtra(PARAM_MINDFULNESS_LEVEL, level);
        ((AlarmManager) getSystemService(Context.ALARM_SERVICE)).set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP, nextAlertTimeMs, makePendingIntent(cmdPlayAlert));
    }

    private int getLevel(Intent i)
    {
        int defaultLevel = AlertTime.maxMindfulnessLevel + 1;
        int level = i.getIntExtra(PARAM_MINDFULNESS_LEVEL, defaultLevel);
        if (level == defaultLevel)
        {
            throw new IllegalArgumentException("Mindfulness level must be provided with intent");
        }
        return level;
    }

    private PendingIntent makePendingIntent(PlayAlert cmd)
    {
        return PendingIntent.getService(this, 1, cmd, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void cancelAlert()
    {
        ((AlarmManager) getSystemService(Context.ALARM_SERVICE)).cancel(
            makePendingIntent(new PlayAlert(this)));
    }
}
