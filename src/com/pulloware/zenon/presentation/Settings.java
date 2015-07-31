package com.pulloware.zenon.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.pulloware.zenon.R;
import com.pulloware.zenon.application.AlertService;

public class Settings extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ((Button)findViewById(R.id.btnStop)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertService.stop(Settings.this);
            }
        });
    }
}