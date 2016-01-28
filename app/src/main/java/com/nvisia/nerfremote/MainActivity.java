package com.nvisia.nerfremote;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AbstractNerfRemoteActivity {

    @Bind(R.id.fire_button) ImageButton mFireButton;
    @Bind(R.id.motor_switch) Switch mMotorSwitch;

    private static Toast sToast;
    private String mSessionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mFireButton.setEnabled(false);

        mMotorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    spinUp();
                else
                    spinDown();
                mFireButton.setEnabled(isChecked);
            }
        });

        mFireButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
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

    public void showToast(String text) {
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        sToast.show();
    }

    @Override
    public void onConnect(Object[] data) {
        super.onConnect(data);
    }

    @Override
    public void onNewConnection(Object[] data) {

    }

    @Override
    public void onNameChange(Object[] data) {

    }

    @Override
    public void onSpunUp(Object[] data) {

    }

    @Override
    public void onSpunDown(Object[] data) {

    }

    @Override
    public void onFireOn(Object[] data) {

    }

    @Override
    public void onFireOff(Object[] data) {

    }

    @Override
    public void onUserDisconnected(Object[] data) {

    }
}
