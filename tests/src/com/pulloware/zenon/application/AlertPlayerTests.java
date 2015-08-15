package com.pulloware.zenon.application;

import android.content.Context;
import android.media.AudioManager;
import android.test.AndroidTestCase;
import com.pulloware.zenon.infrastructure.Trace;

/**
 * Created by sharas on 8/15/15.
 */
public class AlertPlayerTests extends AndroidTestCase
{
    public void testAlertNotPlayedWhenInSilentMode()
    {
        Context c = getContext();
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Trace.on = true;
        AlertPlayer.play(c);
        int plays = Trace.countTopic(AlertPlayer.class.getSimpleName());
        Trace.on = false;
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        assertEquals(0, plays);
    }
}
