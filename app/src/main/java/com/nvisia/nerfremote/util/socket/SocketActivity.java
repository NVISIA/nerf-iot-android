package com.nvisia.nerfremote.util.socket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.URISyntaxException;

import com.nvisia.nerfremote.SettingsActivity;
import com.tramsun.libs.prefcompat.Pref;
import io.socket.client.IO;
import io.socket.client.Socket;

public abstract class SocketActivity extends AppCompatActivity implements SocketInterface {

    protected Socket mSocket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mSocket = IO.socket(socketUri());
        } catch (URISyntaxException e) {
            Log.e("SocketActivity", e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSocket != null) {
            mSocket.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSocket != null) {
            mSocket.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocket != null) {
            mSocket.disconnect();
        }
    }
}
