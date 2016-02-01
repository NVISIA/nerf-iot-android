package com.nvisia.nerfremote.util.nerf;

import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

import com.nvisia.nerfremote.MainActivity;
import com.nvisia.nerfremote.util.socket.SocketActivity;
import com.tramsun.libs.prefcompat.Pref;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import static com.nvisia.nerfremote.SettingsActivity.NAME;

public abstract class AbstractNerfRemoteActivity extends SocketActivity implements NerfRemoteInterface {

    private static final String TAG = "AbstractNerfActivity";

    String mSessionId;

    private Emitter.Listener onConnectListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (args != null && args.length > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onConnect((JSONObject) args[0]);
                    }
                });
            }

        }
    };

    private Emitter.Listener onNewConnectionListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (args != null && args.length > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onNewConnection((JSONObject) args[0]);
                    }
                });
            }
        }
    };

    private Emitter.Listener onNewChangedListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (args != null && args.length > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onNameChange((JSONObject) args[0]);
                    }
                });
            }
        }
    };

    private Emitter.Listener onSpunUpListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (args != null && args.length > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onSpunUp((JSONObject) args[0]);
                    }
                });
            }
        }
    };

    private Emitter.Listener onSpunDownListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (args != null && args.length > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onSpunDown((JSONObject) args[0]);
                    }
                });
            }
        }
    };

    private Emitter.Listener onFireOnListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (args != null && args.length > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onFireOn((JSONObject) args[0]);
                    }
                });
            }
        }
    };

    private Emitter.Listener onFireOffListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (args != null && args.length > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onFireOff((JSONObject) args[0]);
                    }
                });
            }
        }
    };

    private Emitter.Listener onUserDisconnectedListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (args != null && args.length > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onUserDisconnected((JSONObject) args[0]);
                    }
                });
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        if (mSocket != null) {

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
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mSocket != null) {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSocket != null) {
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
    }

    public void emit(String event) {
        if (mSocket != null && mSocket.connected()) {
            mSocket.emit(event);
        }
    }

    public void emit(String event, JSONObject data) {
        if (mSocket != null && mSocket.connected()) {
            mSocket.emit(event, data);
        }
    }

    @Override
    public void onConnect(JSONObject data) {
        Log.d(TAG, "onConnect");
        Log.d(TAG, String.format("Session = %s", mSocket.id()));
        mSessionId = mSocket.id();
    }

    @Override
    public void onNewConnection(JSONObject data) {
        try {
            if (data != null
                    && data.has("participant")
                    && data.getJSONObject("participant").has("id")
                    && ("/#" + mSocket.id()).equals(data.getJSONObject("participant").getString("id")) ) {
                changeName(Pref.getString(NAME));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spinUp() {
        emit("spin_up");
    }

    @Override
    public void spinDown() {
        emit("spin_down");
    }

    @Override
    public void fireOn() {
        emit("fire_on");
    }

    @Override
    public void fireOff() {
        emit("fire_off");
    }

    @Override
    public void changeName(String name) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        emit("name_change", jsonObject);
    }
}
