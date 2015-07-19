package com.pulloware.zenon.infrastructure;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.pulloware.zenon.R;
import com.pulloware.zenon.domain.AlertInterval;
import com.pulloware.zenon.domain.AlertSchedule;
import com.pulloware.zenon.presentation.Main;

/**
 * Created by sharas on 7/14/15.
 */
public class AlertService extends Service
{
    private static final int NOTIFICATION_ID = 111;
    public static final String PARAM_ALERTNESS_LEVEL = "PARAM_ALERTNESS_LEVEL";
    private AlertScheduler aScheduler;

    @Override
    public void onCreate()
    {
        showNotification();
    }

    private void showNotification()
    {
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Zen On")
                .setContentText("Hello World!")
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setOngoing(true);
        Intent showUiIntent = new Intent(this, Main.class)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, showUiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        startForeground(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        int aLevel = intent.getIntExtra(PARAM_ALERTNESS_LEVEL, -1);
        if (aLevel == -1)
        {
            throw new IllegalArgumentException("Alertness level required");
        }
        releaseResources();
        AlertPlayer player = new AlertPlayer(this);
        aScheduler = new AlertScheduler(this, player, new AlertSchedule(new AlertInterval(aLevel)));
        return Service.START_NOT_STICKY;
    }

    private void releaseResources()
    {
        if (aScheduler != null)
        {
            aScheduler.dispose();
        }
    }

    @Override
    public void onDestroy()
    {
        releaseResources();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
