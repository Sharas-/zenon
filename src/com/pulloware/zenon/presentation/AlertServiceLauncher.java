package com.pulloware.zenon.presentation;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import com.pulloware.zenon.R;
import com.pulloware.zenon.application.AlertService;
import com.pulloware.zenon.domain.AlertTime;

public class AlertServiceLauncher extends Activity
{
    private static int defaultLevel = AlertTime.maxMindfulnessLevel / 2;
    private static final int NOTIFICATION_ID = 111;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        startAlerts(AlertTime.minMindfulnessLevel);
        showNotification(AlertTime.minMindfulnessLevel);
    }

    private void startAlerts(int level)
    {
        try
        {
            AlertService.start(level, this);
        } catch (Exception t)
        {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showNotification(int level)
    {
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setOngoing(true)
                .setContent(ControlPanel.makeRemoteViews(this));
        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
