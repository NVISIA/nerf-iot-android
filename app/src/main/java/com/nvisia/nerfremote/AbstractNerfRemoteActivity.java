package com.nvisia.nerfremote;

import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractNerfRemoteActivity extends SocketActivity implements NerfRemoteInterface {
    private static final String TAG = "AbstractNerfActivity";

    String mSessionId;

    private Emitter.Listener onConnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            onConnect(args);
        }
    };

    private Emitter.Listener onNewConnectionListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            onNewConnection(args);
        }
    };

    private Emitter.Listener onNewChangedListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            onNameChange(args);
        }
    };

    private Emitter.Listener onSpunUpListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            onSpunUp(args);
        }
    };

    private Emitter.Listener onSpunDownListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            onSpunDown(args);
        }
    };

    private Emitter.Listener onFireOnListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            onFireOn(args);
        }
    };

    private Emitter.Listener onFireOffListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            onFireOff(args);
        }
    };

    private Emitter.Listener onUserDisconnectedListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            onUserDisconnected(args);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket
                .on("connect", onConnectListener)
                .on("new_connection", onNewConnectionListener)
                .on("name_changed", onNewChangedListener)
                .on("spun_up", onSpunUpListener)
                .on("spun_down", onSpunDownListener)
                .on("fired_on", onFireOnListener)
                .on("fired_off", onFireOffListener)
                .on("user_disconnected", onUserDisconnectedListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSocket
                .off("connect", onConnectListener)
                .off("new_connection", onNewConnectionListener)
                .off("name_changed", onNewChangedListener)
                .off("spun_up", onSpunUpListener)
                .off("spun_down", onSpunDownListener)
                .off("fired_on", onFireOnListener)
                .off("fired_off", onFireOffListener)
                .off("user_disconnected", onUserDisconnectedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket
                .off("connect", onConnectListener)
                .off("new_connection", onNewConnectionListener)
                .off("name_changed", onNewChangedListener)
                .off("spun_up", onSpunUpListener)
                .off("spun_down", onSpunDownListener)
                .off("fired_on", onFireOnListener)
                .off("fired_off", onFireOffListener)
                .off("user_disconnected", onUserDisconnectedListener);
    }

    @Override
    public void onConnect(Object[] data) {
        Log.d(TAG, "onConnect");
        mSessionId = mSocket.id();
    }

    @Override
    public void spinUp() {
        mSocket.emit("spin_up");
    }

    @Override
    public void spinDown() {
        mSocket.emit("spin_down");
    }

    @Override
    public void fireOn() {
        mSocket.emit("fire_on");
    }

    @Override
    public void fireOff() {
        mSocket.emit("fire_off");
    }

    @Override
    public void changeName(String name) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        mSocket.emit("name_change", jsonObject.toString());
    }
}
