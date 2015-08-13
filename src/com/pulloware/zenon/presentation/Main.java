package com.pulloware.zenon.presentation;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.pulloware.zenon.R;

public class Main extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab prefsTab = actionBar.newTab().setText("Preferences");
        ActionBar.Tab aboutTab = actionBar.newTab().setText("About");
        prefsTab.setTabListener(new TabListener(new PreferencesTab()));
        aboutTab.setTabListener(new TabListener(new AboutTab()));
        actionBar.addTab(prefsTab);
        actionBar.addTab(aboutTab);
    }

    public class TabListener implements ActionBar.TabListener
    {
        private Fragment fragment;

        public TabListener(Fragment fragment)
        {
            this.fragment = fragment;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
        {
            ft.replace(R.id.mainContent, fragment);
        }

        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft)
        {
            ft.remove(fragment);
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft)
        {
        }
    }
}