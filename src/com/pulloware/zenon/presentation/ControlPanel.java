package com.pulloware.zenon.presentation;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.pulloware.zenon.R;
import com.pulloware.zenon.application.AlertService;
import com.pulloware.zenon.domain.AlertTime;

/**
 * Created by sharas on 7/30/15.
 */
public class ControlPanel extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String cmd = intent.getAction();
        if (cmd == CmdStop.ACTION)
        {
            AlertService.stop(context);
        }
        else if (cmd == CmdStart.ACTION)
        {
            AlertService.start(CmdStart.getLevel(intent), context);
        }
    }

    public static abstract class PanelCommand extends Intent
    {
        public PanelCommand(Context c, String action)
        {
            super(c, ControlPanel.class);
            super.setAction(action);
        }
    }

    public static class CmdStop extends PanelCommand
    {
        public static String ACTION = "CMD_STOP";

        public CmdStop(Context c)
        {
            super(c, ACTION);
        }
    }

    public static class CmdStart extends PanelCommand
    {
        public static String ACTION = "CMD_START";
        private static String PARAM_LEVEL = "PARAM_LEVEL";

        public CmdStart(Context c, int level)
        {
            super(c, ACTION);
            AlertTime.throwIfLevelInvalid(level);
            super.putExtra(PARAM_LEVEL, level);
        }

        public static int getLevel(Intent i)
        {
            int badLevel = AlertTime.maxMindfulnessLevel + 1;
            int level = i.getIntExtra(PARAM_LEVEL, badLevel);
            if (level == badLevel)
            {
                throw new IllegalArgumentException("Level parameter is not set for intent");
            }
            return level;
        }
    }

    public static RemoteViews makeRemoteViews(Context c)
    {
        RemoteViews rv = new RemoteViews(c.getPackageName(), R.layout.control_panel);
        rv.setOnClickPendingIntent(R.id.btnStop,
            PendingIntent.getBroadcast(c, 0, new CmdStop(c), 0));
        int level = AlertTime.minMindfulnessLevel;
        int[] buttons = new int[]{R.id.btnGo1, R.id.btnGo2, R.id.btnGo3, R.id.btnGo4, R.id.btnGo5};
        for (int i = 0; i < buttons.length; ++i)
        {
            rv.setOnClickPendingIntent(buttons[i],
                PendingIntent.getBroadcast(c, i, new CmdStart(c, level), 0));
            ++level;
        }
        Intent showSettings = new Intent(c, Settings.class)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        rv.setOnClickPendingIntent(R.id.btnSettings,
            PendingIntent.getActivity(c, 0, showSettings, 0));
        return rv;
    }
}

