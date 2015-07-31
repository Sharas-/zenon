package com.pulloware.zenon.application;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.pulloware.zenon.domain.AlertTime;
import com.pulloware.zenon.infrastructure.Scheduler;

/**
 * Created by sharas on 7/14/15.
 */
public class AlertService extends IntentService
{
    private static class PlayAlertCommand extends Intent
    {
        private static final String PARAM_MINDFULNESS_LEVEL = "PARAM_MINDFULNESS_LEVEL";

        public PlayAlertCommand(Context c, int level)
        {
            super(c, AlertService.class);
            AlertTime.throwIfLevelInvalid(level);
            super.putExtra(PARAM_MINDFULNESS_LEVEL, level);
        }

        public static boolean hasLevelParam(Intent i)
        {
            return i.hasExtra(PARAM_MINDFULNESS_LEVEL);
        }

        public static int getLevelParam(Intent i)
        {
            int defaultLevel = AlertTime.maxMindfulnessLevel + 1;
            int level = i.getIntExtra(PARAM_MINDFULNESS_LEVEL, defaultLevel);
            if (level == defaultLevel)
            {
                throw new IllegalArgumentException("Mindfulness level not set for intent");
            }
            return level;
        }
    }

    public AlertService()
    {
        super(AlertService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        new AlertPlayer(this).playAsync();
        if (PlayAlertCommand.hasLevelParam(intent))
        {
            scheduleAlert(PlayAlertCommand.getLevelParam(intent), this);
        }
    }

    private static void scheduleAlert(int mindfulnessLevel, Context c)
    {
        long when = AlertTime.next(mindfulnessLevel) * 1000;
        Log.d("AlertService", "scheduling for level " + mindfulnessLevel + " after " + when / 1000);
        Scheduler.schedule(new PlayAlertCommand(c, mindfulnessLevel), when, c);
    }

    private static void cancelAlert(Context c)
    {
        Log.d("AlertService", "canceling last alert");
        Scheduler.cancel(new PlayAlertCommand(c, AlertTime.maxMindfulnessLevel), c);
    }

    public static void start(int mindfulnessLevel, Context c)
    {
        cancelAlert(c);
        scheduleAlert(mindfulnessLevel, c);
    }

    public static void stop(Context c)
    {
        cancelAlert(c);
    }
}
