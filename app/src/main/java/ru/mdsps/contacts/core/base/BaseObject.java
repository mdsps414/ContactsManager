package ru.mdsps.contacts.core.base;

import org.json.JSONObject;

public abstract class BaseObject {

    private int objectType;

    public static final int CONTACT = 0;
    public static final int RECENT = 1;
    public static final int PHONE = 2;
    public static final int EMAIL = 3;
    public static final int GROUP = 4;
    public static final int ORGANIZATION = 5;
    public static final int CONTACT_LIST_ITEM = 6;

    public abstract String getItemPrimaryLabel();

    public abstract String toString();

    public abstract JSONObject toJSONObject();

    public  int getObjectType(){
        return objectType;
    }

    public void setObjectType(int type){
        this.objectType = type;
    }
}
