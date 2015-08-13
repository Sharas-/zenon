package com.pulloware.zenon.presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.pulloware.zenon.R;
import com.pulloware.zenon.domain.AlertTime;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * Created by sharas on 8/13/15.
 */
public class AboutTab extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        WebView view = new WebView(getActivity());
        view.loadDataWithBaseURL(null, getContent(), "text/html", "utf-8", null);
        return view;
    }

    private String getContent()
    {
        InputStream in_s = getResources().openRawResource(R.raw.about);
        try
        {
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            return MessageFormat.format(new String(b), (Object[]) AlertTime.getIntervals());
        } catch (IOException e)
        {
            return e.getMessage();
        }
    }
}
