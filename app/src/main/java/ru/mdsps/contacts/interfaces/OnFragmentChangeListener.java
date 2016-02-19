package ru.mdsps.contacts.interfaces;

public interface OnFragmentChangeListener {

    int FCL_MAIN_CONTACTS = 1000;
    int FCL_MAIN_GROUPS = 1001;
    int FCL_MAIN_FAVORITES = 1002;

    void onFragmentChange(int fragmentMode);
}
