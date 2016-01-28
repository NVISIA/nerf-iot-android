package com.nvisia.nerfremote;

import java.util.Objects;

import org.json.JSONException;

public interface NerfRemoteInterface {
    public void onConnect(Object[] data);
    public void onNewConnection(Object[] data);
    public void onNameChange(Object[] data);
    public void onSpunUp(Object[] data);
    public void onSpunDown(Object[] data);
    public void onFireOn(Object[] data);
    public void onFireOff(Object[] data);
    public void onUserDisconnected(Object[] data);

    public void spinUp();
    public void spinDown();
    public void fireOn();
    public void fireOff();
    public void changeName(String name) throws JSONException;
}
