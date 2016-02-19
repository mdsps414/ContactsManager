package ru.mdsps.contacts.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mdsps.contacts.core.base.BaseObject;

public class ContactPhone extends BaseObject{

    private String mPhone;
    private int mType;
    private String mLabel;

    public ContactPhone(String phone, int type, String label) {
        mPhone = phone;
        mType = type;
        mLabel = label;
        setObjectType(PHONE);
    }

    public ContactPhone(String phone, int type){
        mPhone = phone;
        mType = type;
        mLabel = "";
        setObjectType(PHONE);
    }

    public ContactPhone(String data){
        setObjectType(PHONE);
        try{
            JSONObject mJSON = new JSONObject(data);
            setPhone(mJSON.getString("mPhone"));
            setType(mJSON.getInt("mType"));
            setLabel(mJSON.getString("mLabel"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getPhone() {
        return mPhone;
    }
    public int getType() {
        return mType;
    }
    public String getLabel() {
        return mLabel;
    }
    public void setPhone(String phone) {
        mPhone = phone;
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
            res.put("mPhone", mPhone);
            res.put("mType", mType);
            res.put("mLabel", mLabel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getItemPrimaryLabel() {
        return getPhone();
    }

}
