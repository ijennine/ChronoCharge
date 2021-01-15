package com.example.chronocharge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

/** Un service qui permet d'executer l'application en arrière plan même si ke programme est fermé**/

public class ForegroundService extends Service {
    private static final int JOB_ID =852;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, BatteryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("ChronoCharge")
                .setContentText(input)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(3, notification);
        //do heavy work on a background thread
        scheduleJobSender();
        //stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    /**Définitiopn de la barre de notification**/
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    /** Mise en place d'un scheduler d'une période T=30s**/
    public void scheduleJobSender() {
        ComponentName serviceName = new ComponentName(getApplicationContext(), BtSenderService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName).setPeriodic(30000).setRequiresCharging(true).build();
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
       /* int result =*/ scheduler.schedule(jobInfo);
        /*if (result == JobScheduler.RESULT_SUCCESS) {
            makeText(getApplicationContext(), "schedule job avec success", LENGTH_LONG).show();
        }*/
    }
}