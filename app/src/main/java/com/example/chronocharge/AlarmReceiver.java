package com.example.chronocharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private BTConnection btComm ;
    private MainActivity comInit = new MainActivity();

    @Override
    public void onReceive(Context context, Intent intent) {
        btComm = comInit.btCommCopie();
        Log.d("Socketreceive",""+btComm.getBluetoothSocket());
        Log.d("outputreceive",""+btComm.getOutputStream());
        Log.d("Inputreceive",""+btComm.getInputStream());
        btComm.sendBatteryLevel("L000");
        Log.d("Btsender","Message alarme...");
        UserActivity inst = UserActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");
    }

}
