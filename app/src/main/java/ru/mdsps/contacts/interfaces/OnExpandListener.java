package ru.mdsps.contacts.interfaces;

public interface OnExpandListener {

    void onExpandMore(int position, int groupId, int itemCount);
    void onExpandLess(int position, int groupId, int itemCount);
}
