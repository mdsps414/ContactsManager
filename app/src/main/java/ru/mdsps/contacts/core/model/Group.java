package ru.mdsps.contacts.core.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mdsps.contacts.core.base.BaseObject;

public class Group extends BaseObject {

    private int mGroupId;
    private String mTitle;
    private int mCountItem;
    private Drawable mImage;
    private String mAccountName;
    private String mAccountType;
    private boolean mExpanded;

    public Group(){
        setObjectType(GROUP);
    }

    public Group(String data){
        try {
            JSONObject res = new JSONObject(data);
            setGroupId(res.getInt("mGroupId"));
            setTitle(res.getString("mTitle"));
            setObjectType(GROUP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getGroupId() {
        return mGroupId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setGroupId(int groupId) {
        this.mGroupId = groupId;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable mImage) {
        this.mImage = mImage;
    }

    public int getCountItem() {
        return mCountItem;
    }

    public void setCountItem(int mCountItem) {
        this.mCountItem = mCountItem;
    }

    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String mAccountName) {
        this.mAccountName = mAccountName;
    }

    public String getAccountType() {
        return mAccountType;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setExpanded(boolean mExpanded) {
        this.mExpanded = mExpanded;
    }

    public void setAccountType(String mAccountType) {
        this.mAccountType = mAccountType;
    }

    @Override
    public String getItemPrimaryLabel() {
        return getTitle();
    }

    @Override
    public String toString() {
        JSONObject res = toJSONObject();
        return res.toString();
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject res = new JSONObject();
        try {
            res.put("mGroupId", getGroupId());
            res.put("mTitle", getTitle());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}
