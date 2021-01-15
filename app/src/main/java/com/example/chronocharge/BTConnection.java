package com.example.chronocharge;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**Classe qui permet de gérer la connexion BT grace qu socket**/
public class BTConnection extends Thread{

    private BluetoothDevice bluetoothDevice;
    static BluetoothSocket bluetoothSocket;
    static InputStream inputStream;
    static OutputStream outputStream;
    private boolean isAlive = true;
    private boolean connectionStatus = false;

/** Constructeur de la classe qui prend en paramètre @BluetoothDevice **/
    public  BTConnection (BluetoothDevice device) {
        try {

                bluetoothDevice = device;
                bluetoothSocket = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid()); // Ouverture de connextion
                bluetoothSocket.connect(); //connextion

                inputStream = bluetoothSocket.getInputStream(); // recuperation du socket de reception message BT
                outputStream = bluetoothSocket.getOutputStream(); // recuperation du socket envoie messsage BT
                connectionStatus = true;
            Log.d("DEBUG", "establish connection");
//                makeText( getApplicationContext(),"connected to "+ device.getName(), LENGTH_LONG ).show();
            } catch (IOException exception) {
                connectionStatus = false;
                Log.e("DEBUG", "Cannot establish connection", exception);
            }
        }

        public boolean connectionStatus(){
        return connectionStatus;
        }

        /** Méthode qui permet d'envoyer un caractère par BT en utilisant le socket d'envoie**/
        public void sendBatteryLevel (char mess) {
            try {
                outputStream.write(mess);
                outputStream.flush();
                Log.e( "DEBUG", "write message" );
            } catch (IOException e) {
                Log.e( "DEBUG", "Cannot write message", e );
            }
        }

        /** Termine la connexion en cours et tue le thread**/

        public void close() {
            try {
                bluetoothSocket.close();
                isAlive = false;
            } catch (IOException e) {
                Log.e( "DEBUG", "Cannot close socket", e );
            }
        }

}


