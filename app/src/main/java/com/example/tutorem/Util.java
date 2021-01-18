package com.example.tutorem;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tutorem.Instrumentation.JSONHandler;

import java.util.List;


public class Util extends JobService {
    private static final String CHANNEL_ID = "TutoRem";
    //both in secounds
    private static final int SCHEDULEINTERVAL = 2*60; //After rescheduling the amount of time to pass before the next call
    private static final int SCHEDULESTART = 1*60; //Start of Timeinterval for first call

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("Util","onStartJob");
        JSONHandler jsonHandler = new JSONHandler(this);
        List<JSONHandler.activity> activities = jsonHandler.getNextActivity(true);
        if(!activities.isEmpty()) {
            addNotification(activities.get(0));
        }
        Util.scheduleJob(this);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    private void addNotification(JSONHandler.activity ac){
        //createNotificationChannel(activity);
        Intent intent = new Intent(this,ShowRemActivity.class);
        intent.putExtra("id",ac.id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_tutorem_green)
                .setContentTitle("REM : "+ac.name)
                .setContentText("Your REM is due at "+(int)ac.intervalHour/100+":"+ac.intervalHour%100)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(0,builder.build());
        }

    }
    public static void scheduleJob(Context context){
        Log.d("UTIL","schedule Job");
        ComponentName serviceComponent = new ComponentName(context, Util.class);
        JobInfo.Builder builder = new JobInfo.Builder(0,serviceComponent);
        builder.setMinimumLatency(SCHEDULESTART * 1000); // wait at least
        builder.setOverrideDeadline((SCHEDULESTART+SCHEDULEINTERVAL) * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }


}
