package ru.mdsps.contacts.core.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.settings.SettingsProvider;

public class Contact extends BaseObject {

    private Long mCID;
    private String mDisplayName;
    private String mDisplayNameAlternative;
    private String mPhoneBookLabel;
    private String mPhoneBookLabelAlternative;
    private String mMobileNumber;
    private String mPhotoUri;
    private String mThumbPhotoUri;
    private Boolean mFirst;

    private String mFirstName;
    private String mMiddleName;
    private String mFamilyName;
    private String mOrganization;
    private String mDepartment;
    private String mGroupName;

    private ArrayList<ContactPhone> mPhoneList;
    private ArrayList<ContactEmail> mEmailList;

    public Contact(){
        setObjectType(CONTACT);
    }

    public Contact(String data){
        setObjectType(CONTACT);
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
            setFirst(mJSON.getBoolean("mFirst"));

            setFirstName(mJSON.getString("mFirstName"));
            setMiddleName(mJSON.getString("mMiddleName"));
            setFamilyName(mJSON.getString("mFamilyName"));
            setOrganization(mJSON.getString("mOrganization"));
            setDepartment(mJSON.getString("mDepartment"));
            setGroupName(mJSON.getString("mGroupName"));

            JSONArray mPhoneJSON = mJSON.getJSONArray("mPhoneList");
            for(int i = 0; i < mPhoneJSON.length(); i++){
                JSONObject mPhone = mPhoneJSON.getJSONObject(i);
                mPhoneList.add(new ContactPhone(mPhone.toString()));
            }

            JSONArray mEmailJSON = mJSON.getJSONArray("mEmailList");
            for(int i = 0; i < mEmailJSON.length(); i++){
                JSONObject mEmail = mEmailJSON.getJSONObject(i);
                mEmailList.add(new ContactEmail(mEmail.toString()));
            }

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

    public String getFirstName() {
        return mFirstName;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public String getFamilyName() {
        return mFamilyName;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public String getGroupName() {
        return mGroupName;
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

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setMiddleName(String mMiddleName) {
        this.mMiddleName = mMiddleName;
    }

    public void setFamilyName(String mFamilyName) {
        this.mFamilyName = mFamilyName;
    }

    public void setOrganization(String mOrganization) {
        this.mOrganization = mOrganization;
    }

    public void setDepartment(String mDepartment) {
        this.mDepartment = mDepartment;
    }

    public void setGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
    }

    public ArrayList<BaseObject> getContactPhones(){
        ArrayList<BaseObject> mResult = null;
        if(mPhoneList != null) {
            mResult = new ArrayList<>(mPhoneList.size());
            for (ContactPhone item : mPhoneList) {
                if (item.getType() == 2) {
                    mResult.add(0, item);
                } else {
                    mResult.add(item);
                }
            }
        }
        return mResult;
    }

    public ContactPhone getContactPhone(int index){
        return mPhoneList.get(index);
    }

    public void addContactPhone(ContactPhone phone){
        if(mPhoneList == null){
            mPhoneList = new ArrayList<>();
        }
        mPhoneList.add(phone);
    }

    public void addContactPhone(int index, ContactPhone phone){
        if(mPhoneList == null){
            mPhoneList = new ArrayList<>();
        }
        mPhoneList.add(index, phone);
    }

    public void removeContactPhone(int index){
        mPhoneList.remove(index);
    }

    public ArrayList<BaseObject> getContactEmail(){
        ArrayList<BaseObject> mResult = new ArrayList<>(mEmailList.size());
        mResult.addAll(mEmailList);
        return mResult;
    }

    public ContactEmail getContactEmail(int index){
        return mEmailList.get(index);
    }

    public void addContactEmail(ContactEmail email){
        if(mEmailList == null){
            mEmailList = new ArrayList<>();
        }
        mEmailList.add(email);
    }

    public void addContactEmail(int index, ContactEmail email){
        if(mEmailList == null){
            mEmailList = new ArrayList<>();
        }
        mEmailList.add(index, email);
    }

    public void removeContactEmail(int index){
        mEmailList.remove(index);
    }


    @Override
    public String getItemPrimaryLabel() {
        SettingsProvider mSettings = new SettingsProvider();
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
            mJSON.put("mFirst", isFirst());
            mJSON.put("mFirstName", getFirstName());
            mJSON.put("mMiddleName", getMiddleName());
            mJSON.put("mFamilyName", getFamilyName());
            mJSON.put("mOrganization", getOrganization());
            mJSON.put("mDepartment", getDepartment());
            mJSON.put("mGroupName", getGroupName());

            JSONArray mPhoneJSON = new JSONArray();
            for(ContactPhone mPhone : mPhoneList){
                mPhoneJSON.put(mPhone.toJSONObject());
            }
            mJSON.put("mPhoneList", mPhoneJSON);

            JSONArray mEmailJSON = new JSONArray();
            for(ContactEmail mEmail : mEmailList){
                mEmailJSON.put(mEmail.toJSONObject());
            }
            mJSON.put("mEmailList", mEmailJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mJSON;
    }
}
