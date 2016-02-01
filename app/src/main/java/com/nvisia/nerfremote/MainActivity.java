package com.nvisia.nerfremote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.nvisia.nerfremote.adapter.NerfEventAdapter;
import com.nvisia.nerfremote.model.NerfEvent;
import com.nvisia.nerfremote.util.nerf.AbstractNerfRemoteActivity;
import com.tramsun.libs.prefcompat.Pref;
import org.json.JSONObject;

import static com.nvisia.nerfremote.SettingsActivity.*;


public class MainActivity extends AbstractNerfRemoteActivity {

    private static final String TAG = "MainActivity";

    @Bind(R.id.fire_button)         ImageButton  mFireButton;
    @Bind(R.id.motor_switch)        Switch       mMotorSwitch;
    @Bind(R.id.toolbar)             Toolbar      mToolbar;
    @Bind(R.id.event_recycler_view) RecyclerView mEventRecyclerView;

    private static Toast            sToast;
    private        NerfEventAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initToolbar();

        mFireButton.setEnabled(false);

        mMotorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spinUp();
                } else {
                    spinDown();
                }
                mFireButton.setEnabled(isChecked);
            }
        });

        mFireButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        fireOn();
                        return false;
                    case MotionEvent.ACTION_UP:
                        fireOff();
                        return false;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initEventLogging();
    }

    private void initToolbar() {
        mToolbar.setTitle("Nerf Remote");
        mToolbar.showOverflowMenu();
        setSupportActionBar(mToolbar);
    }

    private void initEventLogging() {
        if (Pref.getBoolean(LOG)) {
            mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new NerfEventAdapter(new ArrayList<NerfEvent>());
            mEventRecyclerView.setAdapter(mAdapter);
        } else if (mAdapter != null) {
            mAdapter.clearData();
            mAdapter = null;
            mEventRecyclerView.setAdapter(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            launchSettingsActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnect(JSONObject data) {
        super.onConnect(data);
        LogEvent("Connected", data);
    }

    @Override
    public void onNewConnection(JSONObject data) {
        super.onNewConnection(data);
        LogEvent("New connection", data);
    }

    @Override
    public void onNameChange(final JSONObject data) {
        LogEvent("changed name", data);
    }

    @Override
    public void onSpunUp(final JSONObject data) {
        mMotorSwitch.setChecked(true);
        LogEvent("spun up motor", data);
    }

    @Override
    public void onSpunDown(JSONObject data) {
        mMotorSwitch.setChecked(false);
        LogEvent("spun down motor", data);
    }

    @Override
    public void onFireOn(JSONObject data) {
        mFireButton.setPressed(true);
        LogEvent("fired", data);
    }

    @Override
    public void onFireOff(JSONObject data) {
        mFireButton.setPressed(false);
        LogEvent("cease fired", data);
    }

    @Override
    public void onUserDisconnected(JSONObject data) {
        LogEvent("disconnected", data);
    }

    public void LogEvent(String eventName, JSONObject data) {
        if (mAdapter != null) {
            mAdapter.addEvent(new NerfEvent(data, eventName));
            mEventRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }

        Log.i(TAG, String.format("%s: %s", eventName, data == null ? "" : data.toString()));
    }

    public void showToast(String text) {
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        sToast.show();
    }

    @Override
    public String socketUri() {
        if (Pref.getString(HOST).isEmpty()) {
            launchSettingsActivity();
        }
        return String.format("%s:%s", Pref.getString(HOST), Pref.getString(PORT));
    }

    public void launchSettingsActivity() {
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(i);
        finishActivity(0);
    }
}
