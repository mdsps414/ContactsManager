package ru.mdsps.contacts.core.utility;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.model.AccountData;
import ru.mdsps.contacts.core.model.ContactListItem;
import ru.mdsps.contacts.settings.SettingsProvider;

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
        for (Account anA : a) {
            String systemAccountType = anA.type;
            AuthenticatorDescription ad = getAuthenticatorDescription(systemAccountType,
                    accountTypes);
            AccountData data = new AccountData(anA.name, ad);
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
        for (AuthenticatorDescription aDictionary : dictionary) {
            if (aDictionary.type.equals(type)) {
                return aDictionary;
            }
        }
        // No match found
        throw new RuntimeException("Unable to find matching authenticator");
    }

    public static String getTypedLabel(int type){
        Context context = AppContacts.getContext();
        switch(type){
            case Phone.TYPE_HOME:
                return context.getString(R.string.types_home);
            case Phone.TYPE_MOBILE:
                return context.getString(R.string.types_mobile);
            case Phone.TYPE_WORK:
                return context.getString(R.string.types_work);
            default:
                return context.getString(R.string.types_other);
        }
    }

    public static String getVersion(){
        Context context = AppContacts.getContext();
        String pakage = context.getPackageName();
        String result;
        try {
            result = context.getPackageManager().getPackageInfo(pakage, 0).versionName;
        } catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
            result = "uncnown";
        }
        return result;
    }

    public static String selName(ContactListItem mItem){
        SettingsProvider mSettings = new SettingsProvider();
        if(mSettings.getNameAlt() == 0){
            return mItem.getDisplayName();
        } else if(mSettings.getNameAlt() == 1){
            return mItem.getDisplayNameAlternative();
        }
        return null;
    }

    public static String selLetter(ContactListItem mItem){
        SettingsProvider mSettings = new SettingsProvider();
        if(mSettings.getNameAlt() == 0){
            return mItem.getPhoneBookLabel();
        } else if(mSettings.getNameAlt() == 1){
            return mItem.getPhoneBookLabelAlternative();
        }
        return null;
    }

    public static Bitmap generateBitmap(){
        Bitmap mBitmap = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(generateRandomColor());
        return mBitmap;
    }

    public static int generateRandomColor(){
        Context context = AppContacts.getContext();
        int[] mColorArray = new int[]{
                R.color.md_red_700,
                R.color.md_pink_700,
                R.color.md_purple_700,
                R.color.md_deep_purple_700,
                R.color.md_indigo_700,
                R.color.md_blue_700,
                R.color.md_light_blue_700,
                R.color.md_cyan_700,
                R.color.md_teal_700,
                R.color.md_green_700,
                R.color.md_light_green_700,
                R.color.md_lime_700,
                R.color.md_yellow_700,
                R.color.md_amber_700,
                R.color.md_orange_700,
                R.color.md_deep_orange_700,
                R.color.md_brown_700,
                R.color.md_grey_700,
                R.color.md_blue_grey_700
        };

        int mColorIndex = new Random().nextInt(mColorArray.length);
        return ContextCompat.getColor(context, mColorArray[mColorIndex]);
    }




}
