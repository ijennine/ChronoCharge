package com.example.chronocharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.makeText;

public class BatteryActivity extends AppCompatActivity {

    private TextView mTextViewPercentage;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;
    private static int blevel = 200;

    private UserActivity userInstance = UserActivity.instance();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(mBroadcastReceiver,iFilter);
        mTextViewPercentage = (TextView) findViewById(R.id.tv_percentage);
        mProgressBar = (ProgressBar) findViewById(R.id.pb);

    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            int test = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
            blevel =level;
           if(test == 0){
                makeText(getApplicationContext(), "Battery is not charging", Toast.LENGTH_SHORT).show();
                userInstance.stopBtSenderService();
                userInstance.stopMyJob(context,852);
                userInstance.stopForegroundService();
                userInstance.finish();
                BatteryActivity.super.finish();
            }else{
                float percentage = level/ (float) scale;
                mProgressStatus = (int)((percentage)*100);
                mTextViewPercentage.setText("" + mProgressStatus + "%");
                mProgressBar.setProgress(mProgressStatus);
            }
        }
    };





}