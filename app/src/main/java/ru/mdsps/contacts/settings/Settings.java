package ru.mdsps.contacts.settings;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.R;


public class Settings {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public Settings(){
        Context context = AppContacts.getContext();
        String SETTINGS_PREFERENCES = "mds_contacts_preg";
        mPreferences = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    // BASE SETTINGS
    private final String THEME = "THEME";
    private final String SHOW_ACCOUNTS = "SHOW_ACCOUNTS";

    public int getAppTheme(){
        return mPreferences.getInt(THEME, R.style.AppTheme_Standard);
    }

    public void setAppTheme(int theme){
        mEditor.putInt(THEME, theme).apply();
    }

    public ArrayList<String> getShowAccounts(){
        String mShowAcc = mPreferences.getString(SHOW_ACCOUNTS, null);
        ArrayList<String> p = new ArrayList<>();
        if(mShowAcc != null && !mShowAcc.equals("") && !mShowAcc.isEmpty()){
            String[] mShowAccounts = mShowAcc.split("~");
            Collections.addAll(p, mShowAccounts);
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
        mEditor.putString(SHOW_ACCOUNTS, sb.toString()).apply();
    }



    // CONTACT SETTINGS
    private final String NAME_ALT = "NAME_ALTERNATIVE";
    public int getNameAlt(){
        return mPreferences.getInt(NAME_ALT,0);
    }
    public void setNameAlt(int val){
        mEditor.putInt(NAME_ALT, val).apply();
    }

    private final String CONTACT_ITEM_TYPE = "CONTACT_ITEM_TYPE";
    public int getItemType(){
        return mPreferences.getInt(CONTACT_ITEM_TYPE,0);
    }
    public void setItemType(int val){
        mEditor.putInt(CONTACT_ITEM_TYPE, val).apply();
    }

    private final String SHOW_CALL_BUTTON = "SHOW_CALL_BUTTON";
    public boolean showCallButton(){ return mPreferences.getBoolean(SHOW_CALL_BUTTON, false); }
    public void setShowCallButton(boolean val){
        mEditor.putBoolean(SHOW_CALL_BUTTON, val).apply();
    }



}
