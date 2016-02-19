package ru.mdsps.contacts.core.model;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mdsps.contacts.core.base.BaseObject;

public class Group extends BaseObject {

    private int mGroupId;
    private String mTitle;
    private Bitmap mImage;

    public Group(){
        setObjectType(GROUP);
    }

    public Group(int groupId, String title) {
        this.mGroupId = groupId;
        this.mTitle = title;
        this.mImage = null;
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

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
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
            res.put("mImage", getImage().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}
