package com.pulloware.zenon.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.pulloware.zenon.R;
import com.pulloware.zenon.infrastructure.AlertService;

/**
 * Created by sharas on 7/11/15.
 */
public class Main extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btnNow = (Button) findViewById(R.id.btnNow);
        btnNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startAlerts();
            }
        });
    }

    private void startAlerts()
    {
        try
        {

            Intent startIntent = new Intent(this, AlertService.class)
                .putExtra(AlertService.PARAM_ALERTNESS_LEVEL, 1);
            startService(startIntent);
//            List<Long> intervals = Arrays.asList(3000L, 10000L);
//            AlertPlayer player = new AlertPlayer(this);
//            AlertScheduler as = new AlertScheduler(this, player, intervals.iterator());

        } catch (Exception t)
        {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}