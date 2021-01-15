package com.example.chronocharge;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * L'activité principale dans laquelle nous permoettons à l'utilisateur de pouvoir se connecter au périphérique BT
 * Nous avons pour cela :
 * - Activation du péripherique Bt s'il est désactivé
 * - Affichage des périphériques
 * - Connexion au périphérique
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private BluetoothAdapter bluetoothAdapter = null;
    private final int REQUEST_ENABLE_BT = 456;
    private List<BluetoothDevice> knownDevices = null;
    private BluetoothDevice Device = null;
    private List<String> tab = null;
    static BTConnection com = null;

    private Button send = null;
    private TextView statutConnection;
    private ListView deviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            send = (Button) findViewById(R.id.Send);
            send.setOnClickListener(this);
            deviceList = (ListView) findViewById(R.id.deviceList);
            statutConnection = (TextView) findViewById(R.id.lblConnectedDevice);
            statutConnection.setText( "Not connected" );

            send.setEnabled(false);

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if ( ! bluetoothAdapter.isEnabled() ) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT); // Activation du périph Bt s'il ne l'est pas
                statutConnection.setText("Relaunch App");
            }

            knownDevices = new ArrayList<>( bluetoothAdapter.getBondedDevices());
            String deviceName;
            tab = new ArrayList<>();
            for(int i=0;i<knownDevices.size();i++){
                deviceName = knownDevices.get(i).getName();
                tab.add( deviceName );
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tab);
            deviceList.setAdapter(adapter);
            deviceList.setOnItemClickListener( deviceListListener );



    }

    /** Permet d'afficher la liste des périphérique BT et de selctionner un parmi **/

    private ListView.OnItemClickListener deviceListListener = new ListView.OnItemClickListener() {
        @Override public void onItemClick(AdapterView<?> adapter, View view, int arg2, long rowId) {
            String deviceName = tab.get((int) rowId);
            BluetoothDevice device =null;
            for(int i=0;i<knownDevices.size();i++){
                if(knownDevices.get(i).getName().compareTo(deviceName)==0){
                    device = knownDevices.get(i);
                }
            }
            Device = device;
            com = new BTConnection(device);
            if(com.connectionStatus()){
                statutConnection.setText( "Connected to "+device.getName() );
                send.setEnabled(true);
            } else{
                statutConnection.setText( "Not connected" );
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Send:
                Intent userIntent = new Intent(this, UserActivity.class);
                startActivity(userIntent);
                deviceList.setEnabled(false);
                break;
        }

    }

    /** Cette méthode permet de pouvoir recuperer le socket creer pour la communication BT*/
    public BTConnection btCommCopie (){return com;}

}