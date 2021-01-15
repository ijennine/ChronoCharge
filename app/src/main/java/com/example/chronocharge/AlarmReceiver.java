package com.example.chronocharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static android.widget.Toast.makeText;
/**Classe qui permet d'envoyer un message quand l'alarme sonne grace un signal intent recu suite au broadcast**/
public class AlarmReceiver extends BroadcastReceiver {
    private BTConnection btComm = null;
    private MainActivity comInit = new MainActivity();

    @Override
    public void onReceive(Context context, Intent intent) {
        btComm = comInit.btCommCopie(); // Connexion au au périphérique BT
        btComm.sendBatteryLevel('y');
        makeText(context, "Reprise de la charge....", Toast.LENGTH_SHORT).show();
        /*Log.d("Btsender","Message alarme...");
        UserActivity inst = UserActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");*/
    }

}
