package com.pulloware.zenon.application.commands;

import android.content.Context;
import android.content.Intent;
import com.pulloware.zenon.application.AlertService;

/**
 * Created by sharas on 7/28/15.
 */
public class AlertServiceCommand extends Intent
{
    public AlertServiceCommand(Context c)
    {
        super(c, AlertService.class);
    }
}
