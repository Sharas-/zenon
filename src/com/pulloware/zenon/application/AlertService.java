package com.pulloware.zenon.application;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
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
            start(PlayAlertCommand.getLevelParam(intent), this);
        }
    }

    public static void start(int mindfulnessLevel, Context c)
    {
        Scheduler.schedule(new PlayAlertCommand(c, mindfulnessLevel), AlertTime.next(mindfulnessLevel) * 1000, c);
    }

    public static void stop(Context c)
    {
        Scheduler.cancel(new PlayAlertCommand(c, AlertTime.maxMindfulnessLevel), c);
    }
}
