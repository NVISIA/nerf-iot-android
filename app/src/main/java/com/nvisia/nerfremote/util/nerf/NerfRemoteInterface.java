package com.nvisia.nerfremote.util.nerf;

import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public interface NerfRemoteInterface {
    public void onConnect(JSONObject data);
    public void onNewConnection(JSONObject data);
    public void onNameChange(JSONObject data);
    public void onSpunUp(JSONObject data);
    public void onSpunDown(JSONObject data);
    public void onFireOn(JSONObject data);
    public void onFireOff(JSONObject data);
    public void onUserDisconnected(JSONObject data);

    public void spinUp();
    public void spinDown();
    public void fireOn();
    public void fireOff();
    public void changeName(String name) throws JSONException;
}
