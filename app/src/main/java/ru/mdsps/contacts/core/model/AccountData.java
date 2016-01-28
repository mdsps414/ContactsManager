package ru.mdsps.contacts.core.model;

import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import ru.mdsps.contacts.AppContacts;

/**
 * Created by Михаил on 26.01.2016.
 * Класс-описатель акаунтов
 */
public class AccountData {

    private String mName;
    private String mType;
    private CharSequence mTypeLabel;
    private Drawable mIcon;

    public AccountData(String name, AuthenticatorDescription description) {
        mName = name;
        Context context = AppContacts.getContext();

        if (description != null) {
            mType = description.type;

            // The type string is stored in a resource, so we need to convert it into something
            // human readable.
            String packageName = description.packageName;
            PackageManager pm = context.getPackageManager();

            if (description.labelId != 0) {
                mTypeLabel = pm.getText(packageName, description.labelId, null);
                if (mTypeLabel == null) {
                    throw new IllegalArgumentException("LabelID provided, but label not found");
                }
            } else {
                mTypeLabel = "";
            }

            if (description.iconId != 0) {
                mIcon = pm.getDrawable(packageName, description.iconId, null);
                if (mIcon == null) {
                    throw new IllegalArgumentException("IconID provided, but drawable not " +
                            "found");
                }
            } else {
                //mIcon = context.getResources().getDrawable(android.R.drawable.sym_def_app_icon);
                mIcon = ContextCompat.getDrawable(context, android.R.drawable.sym_def_app_icon);
            }
        }
    }

    public String getName() {
        return mName;
    }

    public String getType() {
        return mType;
    }

    public CharSequence getTypeLabel() {
        return mTypeLabel;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public String toString() {
        return mName;
    }
}
