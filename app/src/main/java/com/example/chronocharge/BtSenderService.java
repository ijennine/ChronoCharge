package com.example.chronocharge;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.util.Log;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class BtSenderService extends JobService {

    private final int PLUGGED_AC = 1;
    private final int PLUGGED_USB = 2;
    private final int PLUGGED_WIRELESS = 4;

    private String levelMessage = "L200";
    private int bLevel = 100;
    static BTConnection btComm = null; ;
    private MainActivity comInit = new MainActivity();

    private int level = 200;
    private boolean isCharging = true;

    @Override
    public boolean onStartJob(JobParameters params) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(batteryInfoReceiver,iFilter);
        if(!isCharging){
            super.jobFinished(params,false);
            makeText(getApplicationContext(), "Battery not charging", LENGTH_LONG).show();
        }
        else{
            btComm = comInit.btCommCopie();
            Log.d("BtSenderService", "OnstartJob");
            new maTask().execute();
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("BtSenderService", "OnstopJob");
        return false;
    }

    public class maTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            levelMessage = "L" + bLevel;
            if (isCharging) { // Si téléphone branché
                btComm.sendBatteryLevel(levelMessage);
                Log.d("LEVEL", ""+level);
            } else {
                makeText(getApplicationContext(), "Battery not charging", LENGTH_LONG).show();
                Log.d("BtSender", "Pas en charge");
                btComm.close();
                try {
                    super.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Log.d("BtSender", "Communication terminée");
            }
            return null;
        }

    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            int ac = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
            int usb = BatteryManager.BATTERY_PLUGGED_USB;
            int wireless = BatteryManager.BATTERY_PLUGGED_USB;
            if(ac==PLUGGED_AC || usb == PLUGGED_USB || wireless == PLUGGED_WIRELESS)
                isCharging = true;
            else
                isCharging = false;
            //isCharging = intent.getIntExtra(BatteryManager.EXTRA_STATUS,0)
        }
    };


}


