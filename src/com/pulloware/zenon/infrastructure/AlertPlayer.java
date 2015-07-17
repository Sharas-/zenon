package com.pulloware.zenon.infrastructure;

import android.content.Context;
import android.media.MediaPlayer;
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

    public void play()
    {
        MediaPlayer mp = MediaPlayer.create(c, R.raw.water_drop);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                mp.release();
            }
        });

    }
}
