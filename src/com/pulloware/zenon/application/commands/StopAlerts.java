package com.pulloware.zenon.application.commands;

import android.content.Context;
import com.pulloware.zenon.application.AlertService;

/**
 * Created by sharas on 7/28/15.
 */
public class StopAlerts extends AlertServiceCommand
{
    public StopAlerts(Context c)
    {
        super(c);
        super.setAction(AlertService.CMD_STOP_ALERTS);
    }
}
