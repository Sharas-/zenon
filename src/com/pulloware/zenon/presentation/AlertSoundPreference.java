package com.pulloware.zenon.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.ListPreference;
import android.util.AttributeSet;
import com.pulloware.zenon.R;

public class AlertSoundPreference extends ListPreference
{
    int titleRowIdx = 0, uriRowIdx = 1;
    //pivoted array of titles and sound URIs
    String[][] zSounds =
        {
            {
                "zenon_water_drop",
            },
            {
                "android.resource://com.pulloware.zenon/" + R.raw.water_drop,
            }
        };

    Context mContext;
    int selectedIdx = -1;
    MediaPlayer player;

    public AlertSoundPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder)
    {
        String[][] alerts = getAlerts();
        super.setEntries(alerts[titleRowIdx]);
        super.setEntryValues(alerts[uriRowIdx]);
        super.onPrepareDialogBuilder(builder);
        builder.setSingleChoiceItems(alerts[titleRowIdx], 0, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                selectedIdx = i;
                safeReleasePlayer();
                player = MediaPlayer.create(mContext, Uri.parse(alerts[uriRowIdx][i]));
                if (player != null)
                {
                    player.start();
                }
            }
        });
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        super.onDialogClosed(positiveResult);
        if (positiveResult && selectedIdx != -1)
        {
            String value = super.getEntryValues()[selectedIdx].toString();
            if (callChangeListener(value))
            {
                setValue(value);
            }
        }
        safeReleasePlayer();
    }

    private void safeReleasePlayer()
    {
        if (player != null)
        {
            player.release();
        }
    }

    public String[][] getAlerts()
    {
        RingtoneManager manager = new RingtoneManager(mContext);
        manager.setType(RingtoneManager.TYPE_NOTIFICATION | RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();
        int zSoundCount = zSounds[0].length;
        String[][] alerts = new String[2][cursor.getCount() + zSoundCount];
        int i = 0;
        while (i < zSoundCount)
        {
            alerts[titleRowIdx][i] = zSounds[titleRowIdx][i];
            alerts[uriRowIdx][i] = zSounds[uriRowIdx][i];
            ++i;
        }
        while (cursor.moveToNext())
        {
            alerts[titleRowIdx][i] = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            alerts[uriRowIdx][i] = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" +
                cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            ++i;
        }
        cursor.close();
        return alerts;
    }
}
