package com.pulloware.zenon.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import com.pulloware.zenon.R;

/**
 * Created by sharas on 8/1/15.
 */
public class Settings
{
    public static final String PREF_ALERT_SOUND = "pref_alert_sound";
    public static final String PREF_MINDFULNESS_LEVEL = "pref_mindfulness_level";
    public static final String PREF_SILENT = "pref_silent";

    public static int getLevel(Context c)
    {
        return Integer.parseInt(preferences(c).getString(PREF_MINDFULNESS_LEVEL, ""));
    }

    public static Uri getAlertSound(Context c)
    {
        return Uri.parse(preferences(c).getString(PREF_ALERT_SOUND, ""));
    }

    private static SharedPreferences preferences(Context c)
    {
        return PreferenceManager.getDefaultSharedPreferences(c);
    }

    public static void setDefaults(Context c)
    {
        PreferenceManager.setDefaultValues(c, R.xml.preferences, false);
    }

    public static boolean getSilent(Context c)
    {
        return preferences(c).getBoolean(PREF_SILENT, false);
    }
}
