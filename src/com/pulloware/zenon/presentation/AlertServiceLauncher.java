package com.pulloware.zenon.presentation;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import com.pulloware.zenon.R;
import com.pulloware.zenon.application.AlertService;
import com.pulloware.zenon.domain.AlertTime;
import com.pulloware.zenon.infrastructure.Settings;

public class AlertServiceLauncher extends Activity
{
    private static int defaultLevel = AlertTime.levelCount / 2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        launch(Settings.getLevel(this), this);
    }

    public static void launch(int level, Context c)
    {
        AlertService.start(level, c);
        NotificationPanel.show(level, c);
        announceStart(level, c);
    }

    private static void announceStart(int level, Context c)
    {
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(c)
                .setSmallIcon(R.drawable.icon)
                .setContentText("Zening at level " + level + " " + AlertTime.getInterval(level));
        NotificationManager nManager = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
        nManager.notify(-48, mBuilder.build());
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
        } finally
        {
            nManager.cancel(-48);
        }
    }
}
