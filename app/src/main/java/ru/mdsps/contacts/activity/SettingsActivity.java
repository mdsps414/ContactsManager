package ru.mdsps.contacts.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseActivity;
import ru.mdsps.contacts.core.settings.Settings;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    LinearLayout selNameType;
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initElements();
        settings = new Settings();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.settings_name_type:
                new MaterialDialog.Builder(this)
                        .title(R.string.settings_name_type_title)
                        .items(R.array.settings_name_type)
                        .itemsCallbackSingleChoice(settings.getNameAlt(), new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                settings.setNameAlt(which);
                                return true;
                            }
                        })
                        .positiveText(R.string.dialog_button_ok)
                        .show();
                break;
        }

    }

    private void initElements(){
        selNameType = (LinearLayout) findViewById(R.id.settings_name_type);
        selNameType.setOnClickListener(this);
    }
}
