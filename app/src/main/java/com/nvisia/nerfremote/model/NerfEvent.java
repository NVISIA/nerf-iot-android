package com.nvisia.nerfremote.model;

import org.json.JSONException;
import org.json.JSONObject;

public class NerfEvent {
    public String name;
    public String event;

    public NerfEvent() {
    }

    public NerfEvent(JSONObject data, String eventName) {
        try {
            if (data != null && data.has("participant") && data.getJSONObject("participant").has("name")) {
                name = data.getJSONObject("participant").getString("name");
            } else if (data != null && data.has("name")){
                name = data.getString("name");
            } else {
                name = "";
            }
            event = eventName;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public NerfEvent(String tag, String eventName) {
        name = tag;
        event = eventName;
    }
}
