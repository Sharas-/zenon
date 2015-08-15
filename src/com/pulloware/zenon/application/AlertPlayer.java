package com.pulloware.zenon.application;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.pulloware.zenon.infrastructure.Settings;
import com.pulloware.zenon.infrastructure.Trace;

public class AlertPlayer
{
    private static long[] vibratePattern = new long[]{0, 500, 100, 500};

    public static void play(Context c)
    {
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
        {
            playAlert(c);
        }
    }

    private static void playAlert(Context c)
    {
        if (Trace.on)
        {
            Trace.post(AlertPlayer.class.getSimpleName(), "Playing");
        }
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
