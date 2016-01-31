package ru.mdsps.contacts.activity;

import android.graphics.Color;
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
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseActivity;
import ru.mdsps.contacts.core.model.AccountData;
import ru.mdsps.contacts.core.settings.Settings;
import ru.mdsps.contacts.core.settings.SettingsAccountsAdapter;
import ru.mdsps.contacts.core.utility.AppUtility;

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
        txtTheme.setText(themes[settings.getAppTheme()]);
        String[] nameTypes = getResources().getStringArray(R.array.settings_name_type);
        txtNameType.setText(nameTypes[settings.getNameAlt()]);
        String[] itemTypes = getResources().getStringArray(R.array.settings_contact_item_type);
        txtItemType.setText(itemTypes[settings.getItemType()]);
        selShowCallButtonCheck.setChecked(settings.showCallButton());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.settings_theme: // Настройка темы приложения
                new MaterialDialog.Builder(this)
                        .title(R.string.settings_general_theme_title)
                        .items(R.array.settings_general_theme)
                        .itemsCallbackSingleChoice(settings.getAppTheme(), new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                settings.setAppTheme(which);
                                txtTheme.setText(text);
                                return true;
                            }
                        })
                        .positiveText(R.string.dialog_button_ok)
                        .show();
                break;
            case R.id.settings_accounts: // Настройка аккаунтов, данные которыых будут выводится
                new MaterialDialog.Builder(this)
                        .title(R.string.settings_general_show_account_title)
                        .adapter(new SettingsAccountsAdapter(settings.getShowAccounts()), new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                CheckBox acc_check = (CheckBox) itemView.findViewById(R.id.acc_item_check);
                                if(acc_check.isChecked()){
                                    AccountData item = (AccountData) itemView.getTag();
                                    Toast.makeText(SettingsActivity.this,item.getName(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .positiveText(R.string.dialog_button_ok)
                        .show();

                break;
            case R.id.settings_name_type: // Настройка варианта отображения имени пользователя
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
            case R.id.settings_item_type: // Настройка вида записи контакта
                new MaterialDialog.Builder(this)
                        .title(R.string.settings_contact_item_type_tytle)
                        .items(R.array.settings_contact_item_type)
                        .itemsCallbackSingleChoice(settings.getItemType(), new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                settings.setItemType(which);
                                txtItemType.setText(text);
                                if(which == 1){
                                    selShowCallButton.setEnabled(true);
                                } else {
                                    selShowCallButton.setEnabled(false);
                                }
                                return true;
                            }
                        })
                        .positiveText(R.string.dialog_button_ok)
                        .show();
                break;
            case R.id.settings_show_call: // Настройка вывода кнопки звонка с картинки контакта
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

    private String[] getAccountNames(){


        return null;
    }
}
