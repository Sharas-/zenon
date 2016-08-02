package com.pulloware.zenon.presentation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import com.pulloware.zenon.R;
import com.pulloware.zenon.application.AlertPlayer;
import com.pulloware.zenon.application.AlertService;
import com.pulloware.zenon.domain.AlertTime;

/**
 * Created by sharas on 7/30/15.
 */
public class ControlPanel extends BroadcastReceiver
{
    private static final int NOTIFICATION_ID = -111;

    @Override
    public void onReceive(Context c, Intent intent)
    {
        String cmd = intent.getAction();
        if (cmd == CmdStop.ACTION)
        {
            AlertService.stop(c);
            ((NotificationManager) c.getSystemService(c.NOTIFICATION_SERVICE)).cancel(NOTIFICATION_ID);
        }
        else if (cmd == CmdStart.ACTION)
        {
            int level = CmdStart.getLevel(intent);
            AlertService.start(level, c);
            show(level, c);
            AlertPlayer.play(c);
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
            int badLevel = AlertTime.levelCount + 1;
            int level = i.getIntExtra(PARAM_LEVEL, badLevel);
            if (level == badLevel)
            {
                throw new IllegalArgumentException("Level parameter is not set for intent");
            }
            return level;
        }
    }

    private static RemoteViews makeRemoteViews(Context c, int level)
    {
        AlertTime.throwIfLevelInvalid(level);
        RemoteViews rv = new RemoteViews(c.getPackageName(), R.layout.control_panel);
        rv.setTextViewText(R.id.txtInterval, AlertTime.getInterval(level).toString());
        int[] buttons = new int[]{R.id.btnGo1, R.id.btnGo2, R.id.btnGo3, R.id.btnGo4, R.id.btnGo5};
        int btnTxtColor = c.getResources().getColor(R.color.levelBtnPassive);
        for (int i = 0; i < buttons.length; ++i)
        {
            rv.setOnClickPendingIntent(buttons[i],
                PendingIntent.getBroadcast(c, i, new CmdStart(c, i), 0));
            rv.setTextColor(buttons[i], btnTxtColor);
        }
        rv.setTextColor(buttons[level], c.getResources().getColor(R.color.levelBtnActive));
        return rv;
    }

    public static void show(int level, Context c)
    {
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(c)
                .setSmallIcon(R.drawable.icon)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setDeleteIntent(PendingIntent.getBroadcast(c, 0, new CmdStop(c), 0))
                .setContent(makeRemoteViews(c, level));
        NotificationManager nManager = (NotificationManager) c.getSystemService(c.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

