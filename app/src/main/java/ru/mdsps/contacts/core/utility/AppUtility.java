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

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.model.AccountData;

public class AppUtility {

    private static int[] themeList = new int[]{
            R.style.AppTheme_Standard,
            R.style.AppTheme_Green
    };

    public static int getThemeResource(int theme){
        return themeList[theme];
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

    public static ArrayList<String> getPhoneBookAccountList(){
        ArrayList<String> res = new ArrayList<>();
        Context context = AppContacts.getContext();
        Cursor cur = context.getContentResolver().query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts.ACCOUNT_NAME, ContactsContract.RawContacts.ACCOUNT_TYPE},
                ContactsContract.RawContacts.ACCOUNT_NAME + " IS NOT NULL",
                null,
                ContactsContract.RawContacts.ACCOUNT_NAME + " ASC"
        );
        String selAccount = null;
        while(cur.moveToNext()){
            selAccount = cur.getString(0) + "~" + cur.getString(1);
            if(!res.contains(selAccount)){
                res.add(selAccount);
            }
        }
        cur.close();
        return res;
    }
}
