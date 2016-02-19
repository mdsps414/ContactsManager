package ru.mdsps.contacts.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mdsps.contacts.core.base.BaseObject;


public class ContactEmail extends BaseObject {

    private String mEmail;
    private int mType;
    private String mLabel;

    public ContactEmail(String email, int type, String label) {
        mEmail = email;
        mType = type;
        mLabel = label;
        setObjectType(EMAIL);
    }

    public ContactEmail(String email, int type){
        mEmail = email;
        mType = type;
        mLabel = "";
        setObjectType(EMAIL);
    }

    public ContactEmail(String data){
        setObjectType(EMAIL);
        try{
            JSONObject mEmailJSON = new JSONObject(data);
            setEmail(mEmailJSON.getString("mEmail"));
            setType(mEmailJSON.getInt("mType"));
            setLabel(mEmailJSON.getString("mLabel"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return mEmail;
    }
    public int getType() {
        return mType;
    }
    public String getLabel() {
        return mLabel;
    }
    public void setEmail(String email) {
        mEmail = email;
    }
    public void setType(int type) {
        mType = type;
    }
    public void setLabel(String label) {
        mLabel = label;
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
            res.put("mEmail", mEmail);
            res.put("mType", mType);
            res.put("mLabel", mLabel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getItemPrimaryLabel() {
        return getEmail();
    }

}
