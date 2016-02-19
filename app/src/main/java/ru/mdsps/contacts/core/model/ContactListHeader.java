package ru.mdsps.contacts.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mdsps.contacts.core.base.BaseObject;

public class ContactListHeader extends BaseObject{

    private String mHolderText;

    public ContactListHeader(){
        setObjectType(ALPHABET_HEADER);
    }

    public ContactListHeader(String data){
        try {
            JSONObject res = new JSONObject(data);
            setHolderText(res.getString("mHolderText"));
            setObjectType(ALPHABET_HEADER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getHolderText() {
        return mHolderText;
    }

    public void setHolderText(String mHolderText) {
        this.mHolderText = mHolderText;
    }

    @Override
    public String getItemPrimaryLabel() {
        return getHolderText();
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
            res.put("mHolderText", getHolderText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}
