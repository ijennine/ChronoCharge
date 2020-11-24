package com.example.chronocharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BatteryActivity extends AppCompatActivity {

    private final int PLUGGED_AC = 1;
    private final int PLUGGED_USB = 2;
    private final int PLUGGED_WIRELESS = 4;

    private Context mContext;
    private TextView mTextViewPercentage;

    private boolean isCharging = true;


    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            float percentage = level/ (float) scale;
            mProgressStatus = (int)((percentage)*100);
            mTextViewPercentage.setText("" + mProgressStatus + "%");
            mProgressBar.setProgress(mProgressStatus);
        }
    };

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int ac = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
            int usb = BatteryManager.BATTERY_PLUGGED_USB;
            int wireless = BatteryManager.BATTERY_PLUGGED_WIRELESS;
            /*int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;*/
            //status == BatteryManager.BATTERY_STATUS_FULL;
            Log.d("TEST USB", ""+usb +""+ac+""+""+wireless);
            if(usb == PLUGGED_USB || wireless == PLUGGED_WIRELESS)
                isCharging = true;
            else
                isCharging = false;
            //isCharging = intent.getIntExtra(BatteryManager.EXTRA_STATUS,0)
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        IntentFilter Filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(batteryInfoReceiver,Filter);

        mContext = getApplicationContext();
        mTextViewPercentage = (TextView) findViewById(R.id.tv_percentage);
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver,iFilter);
        mProgressBar = (ProgressBar) findViewById(R.id.pb);

        if(!isCharging){
            System.exit(0);
        }

    }




}