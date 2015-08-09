package com.pulloware.zenon.application;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.pulloware.zenon.infrastructure.Settings;

/**
 * Plays alerts asynchronously in background
 */
public class AlertPlayer
{
    private static long[] vibratePattern = new long[]{0, 500, 100, 500};

    public static void playAsync(Context c)
    {
//        if (Trace.on)
//        {
//            Trace.post(this.getClass().getSimpleName(), "Playing");
//        }
//            Log.d("AlertPlayer", "playing now");
        if (Settings.getSilent(c))
        {
            vibrate(c);
        }
        else
        {
            MediaPlayer.create(c, Settings.getAlertSound(c)).start();
        }
    }

    public static void vibrate(Context c)
    {
        ((Vibrator) c.getSystemService(c.VIBRATOR_SERVICE)).vibrate(vibratePattern, -1);
    }
}
