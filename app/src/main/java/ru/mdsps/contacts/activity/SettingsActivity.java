package ru.mdsps.contacts.activity;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseActivity;
import ru.mdsps.contacts.core.model.AccountData;
import ru.mdsps.contacts.core.settings.OnElementClickListener;
import ru.mdsps.contacts.core.settings.Settings;
import ru.mdsps.contacts.core.settings.SettingsAccountsAdapter;
import ru.mdsps.contacts.core.settings.SettingsColorGridAdapter;
import ru.mdsps.contacts.core.utility.AppUtility;

public class SettingsActivity extends BaseActivity implements View.OnClickListener, OnElementClickListener {

    LinearLayout selTheme, selAccount, selNameType, selItemType, selShowCallButton;
    TextView txtTheme, txtNameType, txtItemType;
    CheckBox selShowCallButtonCheck;
    Settings settings;
    int[] mColorList;
    private int primaryPreselect;
    OnElementClickListener mListener;
    SettingsColorGridAdapter adapterColor;
    SettingsAccountsAdapter adapterAcc;
    GridView mGrid;
    ListView mList;
    ArrayList<String> selAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initElements();
        settings = new Settings();

        mListener = this;

    }

    @Override
    public void onStart(){
        super.onStart();
        txtTheme.setText(AppUtility.getThemeName(settings.getAppTheme()));
        primaryPreselect = settings.getAppTheme();
        mColorList = AppContacts.getContext().getResources().getIntArray(R.array.settings_theme_color_array);
        String[] nameTypes = getResources().getStringArray(R.array.settings_name_type);
        txtNameType.setText(nameTypes[settings.getNameAlt()]);
        String[] itemTypes = getResources().getStringArray(R.array.settings_contact_item_type);
        txtItemType.setText(itemTypes[settings.getItemType()]);
        selShowCallButtonCheck.setChecked(settings.showCallButton());
        selAccounts = settings.getShowAccounts();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.settings_theme: // Настройка темы приложения
                MaterialDialog dialog = new MaterialDialog.Builder(this)
                        .title(R.string.settings_general_theme_title)
                        .customView(R.layout.settings_custom_dialog_view, false)
                        .positiveText(R.string.dialog_button_ok)
                        .build();

                final View vd = dialog.getCustomView();
                mGrid = (GridView) (vd != null ? vd.findViewById(com.afollestad.materialdialogs.commons.R.id.grid) : null);
                adapterColor = new SettingsColorGridAdapter(mColorList, primaryPreselect, mListener);
                mGrid.setAdapter(adapterColor);
                mGrid.setSelector(ResourcesCompat.getDrawable(getResources(), R.drawable.transparent_divider, null));
                dialog.show();
                break;

            case R.id.settings_accounts: // Настройка аккаунтов, данные которыых будут выводится
                MaterialDialog dlg = new MaterialDialog.Builder(this)
                        .title(R.string.settings_general_show_account_title)
                        .customView(R.layout.settings_custom_dialog_view, false)
                        .positiveText(R.string.dialog_button_ok)
                        .build();

                final View view = dlg.getCustomView();
                mGrid = (GridView) (view != null ? view.findViewById(com.afollestad.materialdialogs.commons.R.id.grid) : null);
                if (mGrid != null) {
                    mGrid.setVisibility(View.GONE);
                }
                mList = (ListView) (view != null ? view.findViewById(R.id.list) : null);
                adapterAcc = new SettingsAccountsAdapter(settings.getShowAccounts(), mListener);
                mList.setAdapter(adapterAcc);
                mList.setVisibility(View.VISIBLE);
                dlg.show();

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

    @Override
    public void onElementClick(int type, View v) {
        switch(type) {
            case AppContacts.SETTINGS_LISTENER_COLOR:
                primaryPreselect = (Integer) v.getTag();
                settings.setAppTheme(primaryPreselect);
                setupTheme(primaryPreselect);
                txtTheme.setText(AppUtility.getThemeName(settings.getAppTheme()));
                // Меняем выбор цвета
                adapterColor = new SettingsColorGridAdapter(mColorList, primaryPreselect, mListener);
                mGrid.setAdapter(adapterColor);
                break;
            case AppContacts.SETTINGS_LISTENER_ACCOUNT_SHOW:
                AccountData item = (AccountData) v.getTag();
                String mVal = item.getNameType();
                if(selAccounts == null){
                    selAccounts = new ArrayList<>();
                    selAccounts.add(mVal);
                } else {
                    selAccounts.add(mVal);
                }
                settings.setShowAccounts(selAccounts);
                break;
            case AppContacts.SETTINGS_LISTENER_ACCOUNT_HIDE:
                AccountData item1 = (AccountData) v.getTag();
                String mVal1 = item1.getNameType();
                int pos = selAccounts.indexOf(mVal1);
                selAccounts.remove(pos);
                settings.setShowAccounts(selAccounts);
                break;
        }
    }

    private void setupTheme(int themeColor){
        int[] arrPrimaryDarckColor = getResources().getIntArray(R.array.settings_theme_darck_color_array);
        int[] arrPrimaryWindowColor = getResources().getIntArray(R.array.settings_theme_window_color_array);
        int k = 0;
        for(int i = 0; i < mColorList.length; i++){
            int pColor = mColorList[i];
            if(pColor == themeColor){
                k = i;
            }
        }
        findViewById(R.id.coordinator).setBackgroundColor(arrPrimaryDarckColor[k]);
        findViewById(R.id.scroll).setBackgroundColor(arrPrimaryWindowColor[k]);
        findViewById(R.id.toolbar).setBackgroundColor(themeColor);
    }


}
