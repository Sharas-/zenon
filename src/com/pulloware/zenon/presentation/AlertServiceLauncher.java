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
        launch(Settings.getMindfulnessLevel(this), this);
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
        Toast.makeText(c, "Zening at level " + level + " " + AlertTime.getInterval(level), Toast.LENGTH_SHORT).show();
        AlertPlayer.play(c);
    }
}
