package com.pulloware.zenon.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.pulloware.zenon.R;
import com.pulloware.zenon.application.AlertService;

public class Main extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((Button)findViewById(R.id.btnStop)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertService.stop(Main.this);
            }
        });
    }
}