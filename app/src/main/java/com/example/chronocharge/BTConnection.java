package com.example.chronocharge;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BTConnection extends Thread{

    private BluetoothDevice bluetoothDevice;
    static BluetoothSocket bluetoothSocket;
    static InputStream inputStream;
    static OutputStream outputStream;
    private boolean isAlive = true;
    private boolean connectionStatus = false;


    public  BTConnection (BluetoothDevice device) {
        try {

                this.bluetoothDevice = device;
                this.bluetoothSocket = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
                bluetoothSocket.connect();

                this.inputStream = bluetoothSocket.getInputStream();
                this.outputStream = bluetoothSocket.getOutputStream();
                connectionStatus = true;
            Log.d("DEBUG", "establish connection");
//                makeText( getApplicationContext(),"connected to "+ device.getName(), LENGTH_LONG ).show();
            } catch (IOException exception) {
                connectionStatus = false;
                Log.e("DEBUG", "Cannot establish connection", exception);
            }
        }

    public BTConnection (){
        /*this.bluetoothSocket = bluetoothSocket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;*/
    }

        public boolean connectionStatus(){
        return connectionStatus;
        }

        public void sendBatteryLevel (String mess) {
            try {
                outputStream.write( mess.getBytes() );
                outputStream.flush();
                Log.e( "DEBUG", "write message" );
            } catch (IOException e) {
                Log.e( "DEBUG", "Cannot write message", e );
            }
        }

        // Termine la connexion en cours et tue le thread
        public void close() {
            try {
                bluetoothSocket.close();
                isAlive = false;
            } catch (IOException e) {
                Log.e( "DEBUG", "Cannot close socket", e );
            }
        }

    public BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }

    public void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}


