package com.pulloware.zenon.application;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import com.pulloware.zenon.R;

/**
 * Plays alerts asynchronously in background
 */
public class AlertPlayer
{
    private Context c;

    public AlertPlayer(Context c)
    {
        if (c == null)
        {
            throw new IllegalArgumentException("Cannot create alert player without context");
        }
        this.c = c;
    }

    public void playAsync()
    {
//        if (Trace.on)
//        {
//            Trace.post(this.getClass().getSimpleName(), "Playing");
//        }
        Log.d("AlertPlayer", "playing now");
        MediaPlayer mp = MediaPlayer.create(c, R.raw.water_drop);
        mp.start();
    }
}
