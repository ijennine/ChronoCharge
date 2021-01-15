package com.example.chronocharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class PluggedReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int test = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
        /*UserActivity userInst = UserActivity.instance();
        if(test == 0){
            makeText(context, "Battery is not charging", Toast.LENGTH_SHORT).show();
            userInst.stopBtSenderService();
            userInst.stopMyJob(context,852);
            userInst.stopForegroundService();

            userInst.finish();
        }*/

    }
}
