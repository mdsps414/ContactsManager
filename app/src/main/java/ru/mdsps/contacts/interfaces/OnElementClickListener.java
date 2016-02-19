package ru.mdsps.contacts.interfaces;

import android.view.View;

/**
 * Created by Михаил on 02.02.2016.
 */
public interface OnElementClickListener {

    int SETTINGS_LISTENER_ACCOUNT_SHOW = 1;
    int SETTINGS_LISTENER_ACCOUNT_HIDE = -1;
    int SETTINGS_LISTENER_COLOR = 0;

    void onElementClick(int type, View v);
}
