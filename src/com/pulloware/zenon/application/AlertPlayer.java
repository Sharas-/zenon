package com.pulloware.zenon.application;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.pulloware.zenon.infrastructure.Settings;

public class AlertPlayer
{
    private static long[] vibratePattern = new long[]{0, 500, 100, 250, 100, 250};

    public static void play(Context c)
    {
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
        {
//            if (Trace.on)
//            {
//                Trace.post(AlertPlayer.class.getSimpleName(), "Playing");
//            }
            if (Settings.getSilent(c))
            {
                ((Vibrator) c.getSystemService(c.VIBRATOR_SERVICE)).vibrate(vibratePattern, -1);
            }
            else
            {
                MediaPlayer.create(c, Settings.getAlertSound(c)).start();
            }
        }
    }
}
