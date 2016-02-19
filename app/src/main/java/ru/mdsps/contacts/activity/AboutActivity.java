package ru.mdsps.contacts.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseActivity;
import ru.mdsps.contacts.core.utility.AppUtility;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView about_version = (TextView) findViewById(R.id.about_version);
        TextView about_copyright = (TextView) findViewById(R.id.about_copyright);

        about_version.setText(getString(R.string.fas_version, AppUtility.getVersion()));

        int year = new GregorianCalendar().get(Calendar.YEAR);
        about_copyright.setText(getString(R.string.fas_copyright, year));
    }
}
