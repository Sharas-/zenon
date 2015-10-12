package com.pulloware.zenon.presentation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import com.pulloware.zenon.application.AlertPlayer;
import com.pulloware.zenon.application.AlertService;
import com.pulloware.zenon.domain.AlertTime;
import com.pulloware.zenon.infrastructure.Settings;

public class AlertServiceLauncher extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Settings.setDefaults(this);
        launch(Settings.getLevel(this), this);
        finish();
    }

    public static void launch(int level, Context c)
    {
        AlertService.start(level, c);
        ControlPanel.show(level, c);
        announceStart(level, c);
    }

    private static void announceStart(int level, Context c)
    {
//        int NOTIFICATION_ID = -485;
//        NotificationCompat.Builder mBuilder =
//            new NotificationCompat.Builder(c)
//                .setSmallIcon(R.drawable.icon)
//                .setContentText("Zening at level " + level + " " + AlertTime.getInterval(level));
//        NotificationManager nManager = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
//        nManager.notify(NOTIFICATION_ID, mBuilder.build());
//        try
//        {
//            Thread.sleep(2000);
//        } catch (InterruptedException e)
//        {
//        } finally
//        {
//            nManager.cancel(NOTIFICATION_ID);
//        }
        Toast.makeText(c, "Zening at level " + level + " " + AlertTime.getInterval(level), Toast.LENGTH_SHORT).show();
        AlertPlayer.play(c);
    }
}
