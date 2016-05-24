package com.pulloware.zenon.infrastructure;

import android.content.Context;
import android.net.Uri;

/**
 * Created by sharas on 8/1/15.
 */
public class Settings
{
    public static int getMindfulnessLevel(Context c)
    {
        return 1;
    }

    public static Uri getAlertSound(Context c)
    {
        return Uri.parse("android.resource://com.pulloware.zenon/raw/water_drop");
    }

    public static boolean getIsPrivateMode(Context c)
    {
        return false;
    }
}
