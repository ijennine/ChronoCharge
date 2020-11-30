package com.example.chronocharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class AlarmReceiver extends BroadcastReceiver {
    private BTConnection btComm = null;
    private MainActivity comInit = new MainActivity();

    @Override
    public void onReceive(Context context, Intent intent) {
        btComm = comInit.btCommCopie();
        btComm.sendBatteryLevel("000");
        makeText(context, "Message Alarme....", Toast.LENGTH_SHORT).show();
        //Log.d("Btsender","Message alarme...");
        UserActivity inst = UserActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");
    }

}
