package ru.mdsps.contacts.core.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.mdsps.contacts.settings.Settings;
import ru.mdsps.contacts.core.utility.AppUtility;


public abstract class BaseActivity extends AppCompatActivity {

    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = new Settings();
        setTheme(AppUtility.getThemeResource(settings.getAppTheme()));
        super.onCreate(savedInstanceState);
    }
}
