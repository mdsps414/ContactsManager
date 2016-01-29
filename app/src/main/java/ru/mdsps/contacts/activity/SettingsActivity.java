package ru.mdsps.contacts.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseActivity;
import ru.mdsps.contacts.core.settings.Settings;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    LinearLayout selTheme, selAccount, selNameType, selItemType, selShowCallButton;
    TextView txtTheme, txtNameType, txtItemType;
    CheckBox selShowCallButtonCheck;
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
    public void onStart(){
        super.onStart();
        String[] themes = getResources().getStringArray(R.array.settings_general_theme);
        switch(settings.getAppTheme()){
            case R.style.AppTheme_Standard:
                txtTheme.setText(themes[0]);
                break;
            case R.style.AppTheme_Green:
                txtTheme.setText(themes[1]);
                break;
        }

        String[] nameTypes = getResources().getStringArray(R.array.settings_name_type);
        txtNameType.setText(nameTypes[settings.getNameAlt()]);
        String[] itemTypes = getResources().getStringArray(R.array.settings_contact_item_type);
        txtItemType.setText(itemTypes[settings.getItemType()]);
        selShowCallButtonCheck.setChecked(settings.showCallButton());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.settings_theme:
                new MaterialDialog.Builder(this)
                        .title(R.string.settings_general_theme_title)
                        .items(R.array.settings_general_theme)
                        .itemsCallbackSingleChoice(settings.getNameAlt(), new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                switch(which){
                                    case 0:
                                        settings.setAppTheme(R.style.AppTheme_Standard);
                                        break;
                                    case 1:
                                        settings.setAppTheme(R.style.AppTheme_Green);
                                        break;
                                }
                                txtTheme.setText(text);
                                return true;
                            }
                        })
                        .positiveText(R.string.dialog_button_ok)
                        .show();
                break;
            case R.id.settings_accounts:
                break;
            case R.id.settings_name_type:
                new MaterialDialog.Builder(this)
                        .title(R.string.settings_name_type_title)
                        .items(R.array.settings_name_type)
                        .itemsCallbackSingleChoice(settings.getNameAlt(), new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                settings.setNameAlt(which);
                                txtNameType.setText(text);
                                return true;
                            }
                        })
                        .positiveText(R.string.dialog_button_ok)
                        .show();
                break;
            case R.id.settings_item_type:
                new MaterialDialog.Builder(this)
                        .title(R.string.settings_contact_item_type_tytle)
                        .items(R.array.settings_contact_item_type)
                        .itemsCallbackSingleChoice(settings.getItemType(), new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                settings.setItemType(which);
                                txtItemType.setText(text);
                                return true;
                            }
                        })
                        .positiveText(R.string.dialog_button_ok)
                        .show();
                break;
            case R.id.settings_show_call:
                selShowCallButtonCheck.setChecked(!settings.showCallButton());
                settings.setShowCallButton(selShowCallButtonCheck.isChecked());
                break;

        }

    }

    private void initElements(){
        selTheme = (LinearLayout) findViewById(R.id.settings_theme);
        selTheme.setOnClickListener(this);
        txtTheme = (TextView) findViewById(R.id.settings_theme_desc);
        selAccount = (LinearLayout) findViewById(R.id.settings_accounts);
        selAccount.setOnClickListener(this);
        selNameType = (LinearLayout) findViewById(R.id.settings_name_type);
        selNameType.setOnClickListener(this);
        txtNameType = (TextView) findViewById(R.id.settings_name_type_desc);
        selItemType = (LinearLayout) findViewById(R.id.settings_item_type);
        selItemType.setOnClickListener(this);
        txtItemType = (TextView) findViewById(R.id.settings_item_type_desc);
        selShowCallButton = (LinearLayout) findViewById(R.id.settings_show_call);
        selShowCallButton.setOnClickListener(this);
        selShowCallButtonCheck = (CheckBox) findViewById(R.id.settings_show_call_check);

    }
}
