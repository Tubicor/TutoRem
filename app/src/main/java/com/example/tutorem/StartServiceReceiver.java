package com.example.tutorem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("reciever","got into Receiver");
        //Intent i = new Intent(context,Util.class);
        //context.startService(i);
        Util.scheduleJob(context);
    }
}
