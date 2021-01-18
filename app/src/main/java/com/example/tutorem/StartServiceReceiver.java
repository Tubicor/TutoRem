package com.example.tutorem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartServiceReceiver extends BroadcastReceiver {
    public static boolean started = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        started = true;
        Intent i = new Intent(context,Util.class);
        context.startService(i);
        Util.scheduleJob(context);
    }
}
