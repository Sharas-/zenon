package com.pulloware.zenon.application;

import android.content.Context;
import android.content.Intent;
import android.test.AndroidTestCase;
import com.pulloware.zenon.application.AlertPlayer;
import com.pulloware.zenon.application.AlertService;
import com.pulloware.zenon.infrastructure.Scheduler;
import com.pulloware.zenon.infrastructure.Trace;

/**
 * Created by sharas on 7/27/15.
 */
public class AlertServiceTests extends AndroidTestCase
{
    private static class PlayAlertCommand extends Intent
    {
        public PlayAlertCommand(Context c)
        {
            super(c, AlertService.class);
        }
    }

    public void testAlertPlaysWhenScheduled() throws Exception
    {
        Trace.messages().clear();
        Trace.on = true;
        Context c = this.getContext();
        Scheduler.schedule(new PlayAlertCommand(c), 20, c);
        Thread.sleep(40);
        int plays = Trace.countTopic(AlertPlayer.class.getSimpleName());
        assertEquals(1, plays);
    }

    public void testOnlyOneAlertPlaysWhenRescheduled() throws Exception
    {
        Trace.messages().clear();
        Trace.on = true;
        Context c = this.getContext();
        Scheduler.schedule(new PlayAlertCommand(c), 20, c);
        Scheduler.schedule(new PlayAlertCommand(c), 40, c);
        Thread.sleep(80);
        int plays = Trace.countTopic(AlertPlayer.class.getSimpleName());
        assertEquals(1, plays);
    }

    public void testAlertDoesntPlayWhenStopped() throws Exception
    {
        Trace.messages().clear();
        Trace.on = true;
        Context c = this.getContext();
        Scheduler.schedule(new PlayAlertCommand(c), 20, c);
        AlertService.stop(c);
        Thread.sleep(40);
        int plays = Trace.countTopic(AlertPlayer.class.getSimpleName());
        assertEquals(0, plays);
    }


}
