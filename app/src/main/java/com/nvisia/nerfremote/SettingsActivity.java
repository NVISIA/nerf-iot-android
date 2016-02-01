package com.nvisia.nerfremote;

import android.os.Bundle;
import android.view.*;

import java.util.Arrays;

import com.github.dkharrat.nexusdialog.FormWithAppCompatActivity;
import com.github.dkharrat.nexusdialog.controllers.*;
import com.tramsun.libs.prefcompat.Pref;

public class SettingsActivity extends FormWithAppCompatActivity {

    public final static String NAME = "name";
    public final static String HOST = "host";
    public final static String PORT = "port";
    public final static String LOG = "log";

    @Override
    protected void initForm() {
        FormSectionController section = new FormSectionController(this, "Settings");
        section.addElement(new EditTextController(this, NAME, "Name", "", true));
        section.addElement(new EditTextController(this, HOST, "Host", "", true));
        section.addElement(new EditTextController(this, PORT, "Port", "", true));
        section.addElement(new SelectionController(this, LOG, "Log", true, "On/Off", Arrays.asList("On", "Off"), Arrays.asList(true, false)));

        getFormController().addSection(section);

        setTitle("Network Settings");

        getModel().setValue(NAME, Pref.getString(NAME));
        getModel().setValue(HOST, Pref.getString(HOST));
        getModel().setValue(PORT, Pref.getString(PORT));
        getModel().setValue(LOG, Pref.getBoolean(LOG));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        super.onOptionsItemSelected(item);

        if (getFormController().isValidInput()) {
            String name = (String) getModel().getValue(NAME);
            String host = (String) getModel().getValue(HOST);
            String port = (String) getModel().getValue(PORT);
            boolean log = (boolean) getModel().getValue(LOG);

            Pref.putString(NAME, name);
            Pref.putString(HOST, host);
            Pref.putString(PORT, port);
            Pref.putBoolean(LOG, log);
            finish();
        } else {
            getFormController().showValidationErrors();
        }
        return true;
    }



}
