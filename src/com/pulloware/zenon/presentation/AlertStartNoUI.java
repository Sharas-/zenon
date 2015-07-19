package com.pulloware.zenon.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.pulloware.zenon.domain.AlertInterval;
import com.pulloware.zenon.infrastructure.AlertService;

public class AlertStartNoUI extends Activity
{
    private static int defaultLevel = AlertInterval.maxLevel / 2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        startAlerts(defaultLevel);
    }

    private void startAlerts(int level)
    {
        try
        {
            AlertInterval aInterval = new AlertInterval(level);
            Intent startIntent = new Intent(this, AlertService.class)
                .putExtra(AlertService.PARAM_ALERTNESS_LEVEL, level);
            startService(startIntent);

        } catch (Exception t)
        {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
