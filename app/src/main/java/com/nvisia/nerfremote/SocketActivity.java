package com.nvisia.nerfremote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketActivity extends AppCompatActivity {

    protected Socket mSocket;
    protected static final String uri = "http://10.10.0.215:3000";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mSocket = IO.socket(uri);
        } catch (URISyntaxException e) {  e.printStackTrace(); }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSocket.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSocket.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }
}
