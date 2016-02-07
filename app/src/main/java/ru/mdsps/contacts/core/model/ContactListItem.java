package ru.mdsps.contacts.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.settings.Settings;


public class ContactListItem extends BaseObject {

    private Long mCID;
    private String mDisplayName;
    private String mDisplayNameAlternative;
    private String mPhoneBookLabel;
    private String mPhoneBookLabelAlternative;
    private String mMobileNumber;
    private String mPhotoUri;
    private String mThumbPhotoUri;
    private Boolean mFirst;

    public ContactListItem(){
        setObjectType(CONTACT_LIST_ITEM);
    }

    public ContactListItem(String data){
        setObjectType(CONTACT_LIST_ITEM);
        try{
            JSONObject mJSON = new JSONObject(data);
            setCID(mJSON.getLong("mCID"));
            setDisplayName(mJSON.getString("mDisplayName"));
            setDisplayNameAlternative(mJSON.getString("mDisplayNameAlternative"));
            setPhoneBookLabel(mJSON.getString("mPhoneBookLabel"));
            setPhoneBookLabelAlternative(mJSON.getString("mPhoneBookLabelAlternative"));
            setMobileNumber(mJSON.getString("mMobileNumber"));
            setPhotoUri(mJSON.getString("mPhotoUri"));
            setThumbPhotoUri(mJSON.getString("mThumbPhotoUri"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Long getCID() {
        return mCID;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getDisplayNameAlternative() {
        return mDisplayNameAlternative;
    }

    public String getPhoneBookLabel() {
        return mPhoneBookLabel;
    }

    public String getPhoneBookLabelAlternative() {
        return mPhoneBookLabelAlternative;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public String getThumbPhotoUri() {
        return mThumbPhotoUri;
    }

    public Boolean isFirst() {
        return mFirst;
    }

    public void setCID(Long mCID) {
        this.mCID = mCID;
    }

    public void setDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    public void setDisplayNameAlternative(String mDisplayNameAlternative) {
        this.mDisplayNameAlternative = mDisplayNameAlternative;
    }

    public void setPhoneBookLabel(String mPhoneBookLabel) {
        this.mPhoneBookLabel = mPhoneBookLabel;
    }

    public void setPhoneBookLabelAlternative(String mPhoneBookLabelAlternative) {
        this.mPhoneBookLabelAlternative = mPhoneBookLabelAlternative;
    }

    public void setMobileNumber(String mMobileNumber) {
        this.mMobileNumber = mMobileNumber;
    }

    public void setPhotoUri(String mPhotoUri) {
        this.mPhotoUri = mPhotoUri;
    }

    public void setThumbPhotoUri(String mThumbPhotoUri) {
        this.mThumbPhotoUri = mThumbPhotoUri;
    }

    public void setFirst(Boolean mFirst) {
        this.mFirst = mFirst;
    }

    @Override
    public String getItemPrimaryLabel() {
        Settings mSettings = new Settings();
        if(mSettings.getNameAlt() == 0){
            return getDisplayName();
        } else {
            return getDisplayNameAlternative();
        }
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject mJSON = new JSONObject();
        try{
            mJSON.put("mCID", getCID());
            mJSON.put("mDisplayName", getDisplayName());
            mJSON.put("mDisplayNameAlternative", getDisplayNameAlternative());
            mJSON.put("mPhoneBookLabel", getPhoneBookLabel());
            mJSON.put("mPhoneBookLabelAlternative", getPhoneBookLabelAlternative());
            mJSON.put("mMobileNumber", getMobileNumber());
            mJSON.put("mPhotoUri", getPhotoUri());
            mJSON.put("mThumbPhotoUri", getThumbPhotoUri());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mJSON;
    }
}
