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

/** Service permet de gérer l'envoi des données par BT en de manière périodique*/

public class BtSenderService extends JobService {

    private char levelMessage = 'n';
    private int bLevel = 100;
    //private int compteur  = 0;
    private String levelMessage = "100";
    private int bLevel ;
    static BTConnection btComm = null; ;
    private MainActivity comInit = new MainActivity();


    @Override
    public boolean onStartJob(JobParameters params) {
        btComm = comInit.btCommCopie();
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(levelReceiver,iFilter);
        Log.d("BtSenderService", "OnstartJob");
        new maTask().execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("BtSenderService", "OnstopJob");
        return true;
    }
/**Définition d'un processus pour envoyer les données par BT en arrière plan**/
    public class maTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            if(bLevel<65) {
                levelMessage = 'y'; // Charge
            }
            else if(bLevel >=65 ){
                levelMessage = 'n'; // ne charge pas
            }

            btComm.sendBatteryLevel(levelMessage); // envoie de message
            Log.d("LEVEL", ""+bLevel);
            return null;
        }
    }
    /** Lecture du niveau de batterie grace à broadcast qui est appéle suite à un evenement sur la battérie**/
    private BroadcastReceiver levelReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            bLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        }
    };


}


