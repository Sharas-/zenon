package com.pulloware.zenon.presentation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import com.pulloware.zenon.R;
import com.pulloware.zenon.application.AlertPlayer;
import com.pulloware.zenon.domain.AlertTime;
import com.pulloware.zenon.infrastructure.Settings;

/**
 * Created by sharas on 8/1/15.
 */
public class Preferences extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        ListPreference levelPref = (ListPreference) findPreference(Settings.PREF_MINDFULNESS_LEVEL);
        CharSequence[] values = new CharSequence[AlertTime.levelCount];
        CharSequence[] entries = new CharSequence[AlertTime.levelCount];
        for (int level = 0; level < AlertTime.levelCount; ++level)
        {
            values[level] = Integer.toString(level);
            entries[level] = makeLevelSummary(level);
        }
        levelPref.setEntries(entries);
        levelPref.setEntryValues(values);
        findPreference(Settings.PREF_MINDFULNESS_LEVEL).setSummary(makeLevelSummary(Settings.getLevel(getActivity())));
    }

    private String makeLevelSummary(int level)
    {
        return "level " + level + " " + AlertTime.getInterval(level);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
            .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
            .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        Context c = getActivity();
        if (key.equals(Settings.PREF_MINDFULNESS_LEVEL))
        {
            int level = Settings.getLevel(c);
            findPreference(key).setSummary(makeLevelSummary(level));
            AlertServiceLauncher.launch(level, c);
        }
        else if (key.equals(Settings.PREF_SILENT))
        {
            if (Settings.getSilent(c))
            {
                AlertPlayer.vibrate(c);
            }
        }
    }
}
