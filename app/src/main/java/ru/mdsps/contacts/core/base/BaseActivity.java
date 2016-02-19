package ru.mdsps.contacts.core.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.mdsps.contacts.settings.SettingsProvider;
import ru.mdsps.contacts.core.utility.AppUtility;


public abstract class BaseActivity extends AppCompatActivity {

    SettingsProvider mSettingsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSettingsProvider = new SettingsProvider();
        setTheme(AppUtility.getThemeResource(mSettingsProvider.getAppTheme()));
        super.onCreate(savedInstanceState);
    }
}
