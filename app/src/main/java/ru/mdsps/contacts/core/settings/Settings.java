package ru.mdsps.contacts.core.settings;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.R;

/**
 * Created by Михаил on 27.01.2016.
 * Настройки приложения
 */
public class Settings {

    private final String SETTINGS_PREFERENCES = "mds_contacts_preg";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Settings(){
        Context context = AppContacts.getContext();
        this.preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    // BASE SETTINGS
    private final String THEME = "THEME";
    private final String SHOW_ACCOUNTS = "SHOW_ACCOUNTS";

    public int getAppTheme(){
        return preferences.getInt(THEME, R.style.AppTheme_Standard);
    }

    public void setAppTheme(int theme){
        editor.putInt(THEME, theme).commit();
    }

    public Set<String> getShowAccounts(){
        return preferences.getStringSet(SHOW_ACCOUNTS, null);
    }

    public void setShowAccounts(Set<String> accounts){
        editor.putStringSet(SHOW_ACCOUNTS, accounts).commit();
    }



    // NAME SETTINGS
    private final String NAME_ALT = "NAME_ALTERNATIVE";

    public boolean isNameAlt(){
        return preferences.getBoolean(NAME_ALT, true);
    }

    public void setNameAlt(boolean val){
        editor.putBoolean(NAME_ALT, val);
    }



}
