package ru.mdsps.contacts.core.settings;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
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
        editor.putInt(THEME, theme).apply();
    }

    public ArrayList<String> getShowAccounts(){
        String sha = preferences.getString(SHOW_ACCOUNTS, null);
        ArrayList<String> p = new ArrayList<>();
        if(sha != null){
            String[] shaa = sha.split("~");
            for(String it : shaa){
                p.add(it);
            }
            return p;
        }
        return null;
    }

    public void setShowAccounts(ArrayList<String> accounts){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < accounts.size(); i++){
            sb.append(accounts.get(i));
            if(i < accounts.size()-1) {
                sb.append("~");
            }
        }
        editor.putString(SHOW_ACCOUNTS, sb.toString()).apply();
    }



    // CONTACT SETTINGS
    private final String NAME_ALT = "NAME_ALTERNATIVE";
    public int getNameAlt(){
        return preferences.getInt(NAME_ALT,0);
    }
    public void setNameAlt(int val){
        editor.putInt(NAME_ALT, val).apply();
    }

    private final String CONTACT_ITEM_TYPE = "CONTACT_ITEM_TYPE";
    public int getItemType(){
        return preferences.getInt(CONTACT_ITEM_TYPE,0);
    }
    public void setItemType(int val){
        editor.putInt(CONTACT_ITEM_TYPE, val).apply();
    }

    private final String SHOW_CALL_BUTTON = "SHOW_CALL_BUTTON";
    public boolean showCallButton(){ return preferences.getBoolean(SHOW_CALL_BUTTON, false); }
    public void setShowCallButton(boolean val){
        editor.putBoolean(SHOW_CALL_BUTTON, val).apply();
    }



}
