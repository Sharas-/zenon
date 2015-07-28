package com.pulloware.zenon.application.commands;

import android.content.Context;
import com.pulloware.zenon.application.AlertService;
import com.pulloware.zenon.domain.AlertTime;

/**
 * Created by sharas on 7/23/15.
 */
public class StartAlerts extends AlertServiceCommand
{
    public StartAlerts(Context c, int level)
    {
        super(c);
        AlertTime.throwIfLevelInvalid(level);
        super.putExtra(AlertService.PARAM_MINDFULNESS_LEVEL, level);
        super.setAction(AlertService.CMD_START_ALERTS);
    }
}
