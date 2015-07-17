package com.pulloware.zenon.infrastructure;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
    private AlertScheduler ascheduler;

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
        //display main activity on click with preserved back stack
        Intent resultIntent = new Intent(this, Main.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this)
            .addParentStack(Main.class)
            .addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        startForeground(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        int alevel = intent.getIntExtra(PARAM_ALERTNESS_LEVEL, -1);
        if(alevel == -1)
        {
            throw new IllegalArgumentException("Alertness level required");
        }
        AlertPlayer player = new AlertPlayer(this);
        ascheduler = new AlertScheduler(this, player, new AlertSchedule(new AlertInterval(alevel)));
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        ascheduler.dispose();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
