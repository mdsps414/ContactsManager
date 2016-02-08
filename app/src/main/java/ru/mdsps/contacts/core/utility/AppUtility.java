package ru.mdsps.contacts.core.utility;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.model.AccountData;

public class AppUtility {

    private static int[] themeList = new int[]{
            R.style.AppTheme_Red,
            R.style.AppTheme_Pink,
            R.style.AppTheme_Purple,
            R.style.AppTheme_Deep_Purple,
            R.style.AppTheme_Indigo,
            R.style.AppTheme_Blue,
            R.style.AppTheme_Light_Blue,
            R.style.AppTheme_Cyan,
            R.style.AppTheme_Teal,
            R.style.AppTheme_Green,
            R.style.AppTheme_Light_Green,
            R.style.AppTheme_Lime,
            R.style.AppTheme_Yellow,
            R.style.AppTheme_Amber,
            R.style.AppTheme_Orange,
            R.style.AppTheme_Deep_Orange,
            R.style.AppTheme_Brown,
            R.style.AppTheme_Grey,
            R.style.AppTheme_Blue_Grey
    };

    public static int getThemeResource(int primaryColor){
        Context context = AppContacts.getContext();
        int[] arrPrimaryColor = context.getResources().getIntArray(R.array.settings_theme_color_array);
        int k = 0;
        for(int i = 0; i < arrPrimaryColor.length; i++){
            int pColor = arrPrimaryColor[i];
            if(pColor == primaryColor){
                k = i;
            }
        }
        return themeList[k];
    }

    public static String getThemeName(int primaryColor){
        Context context = AppContacts.getContext();
        int[] arrPrimaryColor = context.getResources().getIntArray(R.array.settings_theme_color_array);
        String[] themes = context.getResources().getStringArray(R.array.settings_general_theme);
        int k = 0;
        for(int i = 0; i < arrPrimaryColor.length; i++){
            int pColor = arrPrimaryColor[i];
            if(pColor == primaryColor){
                k = i;
            }
        }
        return themes[k];
    }

    public static ArrayList<AccountData> getAccountList(){
        Context context = AppContacts.getContext();
        AccountManager am = AccountManager.get(context);
        Account[] a = am.getAccounts();
        ArrayList<AccountData> mAccountList = new ArrayList<>();

        AuthenticatorDescription[] accountTypes = am.getAuthenticatorTypes();
        for (int i = 0; i < a.length; i++) {
            String systemAccountType = a[i].type;
            AuthenticatorDescription ad = getAuthenticatorDescription(systemAccountType,
                    accountTypes);
            AccountData data = new AccountData(a[i].name, ad);
            mAccountList.add(data);
        }

        // Добавляем акаунт телефона
        Drawable icon = ContextCompat.getDrawable(context, R.drawable.vector_ic_call_grey);
        AccountData phone = new AccountData("Phone", "Local Phone Account", "Local Phone", icon);
        mAccountList.add(0, phone);

        return mAccountList;
    }

    private static AuthenticatorDescription getAuthenticatorDescription(String type,
                                                                        AuthenticatorDescription[] dictionary) {
        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i].type.equals(type)) {
                return dictionary[i];
            }
        }
        // No match found
        throw new RuntimeException("Unable to find matching authenticator");
    }

    public static int getRandomColor(){
        Context context = AppContacts.getContext();
        int[] mColorArray = context.getResources().getIntArray(R.array.settings_theme_color_array);
        int mColorIndex = new Random().nextInt(mColorArray.length);
        return mColorArray[mColorIndex];
    }


}
