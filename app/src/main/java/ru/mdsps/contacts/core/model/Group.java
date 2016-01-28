package ru.mdsps.contacts.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mdsps.contacts.core.base.BaseObject;

public class Group extends BaseObject {

    private int groupId;
    private String title;

    public Group(){
        setObjectType(GROUP);
    }

    public Group(int groupId, String title) {
        this.groupId = groupId;
        this.title = title;
        setObjectType(GROUP);
    }

    public Group(String data){
        try {
            JSONObject res = new JSONObject(data);
            setGroupId(res.getInt("groupId"));
            setTitle(res.getString("title"));
            setObjectType(GROUP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getGroupId() {
        return groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setTitle(String title) {
        this.title = title;
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
            res.put("groupId", getGroupId());
            res.put("title", getTitle());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}
