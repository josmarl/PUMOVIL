package union.union_vr1.BlueTooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import union.union_vr1.R;

public class BtConexion extends Activity {

    // will show the statuses
    TextView myLabel;

    // will enable user to enter any text to be printed
    EditText myTextbox;

    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    public void total(){
        try {
            findBT();
            openBT();
            sendData();
            closeBT();
        } catch (IOException ex) {

        }

    }

    // This will find a bluetooth printer device
    public void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
                myLabel.setText("No bluetooth adapter available");
            }

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                    .getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // MP300 is the name of the bluetooth printer device
                    if (device.getName().equals("Star Micronics")) {
                        mmDevice = device;
                        break;
                    }
                }
            }
            myLabel.setText("Bluetooth Device Found");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tries to open a connection to the bluetooth printer device
    public void openBT() throws IOException {
        try {
            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            myLabel.setText("Bluetooth Opened");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // After opening a connection to bluetooth printer device,
    // we have to listen and check if a data were sent to be printed.
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // This is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted()
                            && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length);
                                        final String data = new String(
                                                encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * This will send data to be printed by the bluetooth printer
     */
    public void sendData() throws IOException {
        try {

            // the text typed by the user
            String msg = myTextbox.getText().toString();
            msg += "\n";


            String texto = ".\n";
            texto += "    UNIVERSIDAD PERUANA UNION   \n";
            texto += "     Cent.aplc. Prod. Union     \n";
            texto += "   C. Central Km 19 Villa Union \n";
            texto += " Lurigancho-Chosica Fax: 6186311\n";
            texto += "      Telf: 6186309-6186310     \n";
            texto += " Casilla 3564, Lima 1, LIMA PERU\n";
            texto += "         RUC: 20138122256       \n";
            texto += "--------------------------------\n";
            texto += "Factura Nro. 030-000212\n";
            texto += "Fecha: 12/11/2014\n";
            texto += "Cajero: Juan Perez Perez\n";
            texto += "Cliente: Perico Palotes Palotes\n";
            texto += "DNI: 47678934\n";
            texto += "Direccion: Alameda Nro 2039 - Chosica\n";
            texto += "--------------------------------\n";
            texto += "Cant. Producto           Importe\n";
            texto += "--------------------------------\n";
            texto += String.format("%-6s", 5) + String.format("%-19s", "Pan Americano Mediano Union".substring(0, 17) + ".") + String.format("%7s", 40.50) + "\n";
            texto += String.format("%-6s", 5) + String.format("%-19s", "Rollo de caneladddddddddd".substring(0, 17) + ".") + String.format("%7s", 30.50) + "\n";
            texto += String.format("%-6s", 5) + String.format("%-19s", "Paneton Unioneeeeeeeeee".substring(0, 17) + ".") + String.format("%7s", 10.50) + "\n";
            texto += String.format("%-6s", 5) + String.format("%-19s", "Paneton Super Bom".substring(0, 17) + ".") + String.format("%7s", 3.40) + "\n";
            texto += String.format("%-6s", 5) + String.format("%-19s", "Pan Americano Sandwich".substring(0, 17) + ".") + String.format("%7s", 100.3) + "\n";
            texto += String.format("%-6s", 5) + String.format("%-19s", "Pan Americano Mediano Union".substring(0, 17) + ".") + String.format("%7s", 2.34) + "\n";
            texto += String.format("%-25s", "SUB TOTAL:") + String.format("%7s", 1000.00) + "\n";
            texto += String.format("%-25s", "IGV:") + String.format("%7s", 180.00) + "\n";
            texto += String.format("%-25s", "TOTAL:") + String.format("%7s", 1800.00) + "\n";


            mmOutputStream.write(texto.getBytes());

            // tell the user data were sent
            myLabel.setText("Data Sent");

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Close the connection to bluetooth printer.
    public void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            myLabel.setText("Bluetooth Closed");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}