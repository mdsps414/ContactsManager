package ru.mdsps.contacts.loaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Groups;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.AccountData;
import ru.mdsps.contacts.core.model.ContactListItem;
import ru.mdsps.contacts.core.model.Group;
import ru.mdsps.contacts.core.utility.AppUtility;
import ru.mdsps.contacts.settings.SettingsProvider;

public class GroupListLoader extends AsyncTaskLoader<HashMap<Integer, Object>> {

    private final int GROUP_OBJECTS = -1;

    private String TAG = "GroupListLoader";
    private ContentResolver mContentResolver;
    private SettingsProvider mSettings;
    private ArrayList<AccountData> mAccounts;
    private HashMap<Integer, ArrayList<ContactListItem>> groupCluster;
    private HashMap<Integer, Object> mRecords;

    public GroupListLoader(Context context) {
        super(context);
        mContentResolver = getContext().getContentResolver();
        mSettings = new SettingsProvider();
        mAccounts = AppUtility.getAccountList();
        mRecords = new HashMap<>();
    }

    @Override
    public HashMap<Integer, Object> loadInBackground() {


        Uri URI;
        String[] PROJECTION;

        URI = Groups.CONTENT_URI;
        PROJECTION = new String[]{
                Groups._ID,
                Groups.TITLE,
                Groups.ACCOUNT_TYPE,
                Groups.ACCOUNT_NAME
        };
        ArrayList<BaseObject> mGroups = new ArrayList<>();
        Cursor mGroupsCursor = mContentResolver.query(URI, PROJECTION, null, null, null);
        if(mGroupsCursor != null) {
            while (mGroupsCursor.moveToNext()) {
                int gId = mGroupsCursor.getInt(0);
                int mGroupCounter = getCountGroupItems(gId);
                if(mGroupCounter > 0) {
                    Group mItem = new Group();
                    mItem.setGroupId(mGroupsCursor.getInt(0));
                    mItem.setTitle(mGroupsCursor.getString(1));
                    mItem.setAccountType(mGroupsCursor.getString(2));
                    mItem.setAccountName(mGroupsCursor.getString(3));
                    mItem.setImage(getAccountImage(mItem.getAccountType()));
                    mItem.setCountItem(mGroupCounter);
                    mGroups.add(mItem);
                }
            }
            mGroupsCursor.close();
            mRecords.put(GROUP_OBJECTS, mGroups);
        }

        return mRecords;
    }

    @Override
    public void forceLoad() {
        Log.d(TAG, "forceLoad");
        super.forceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(TAG, "onStopLoading");
    }

    @Override
    public void deliverResult(HashMap<Integer, Object> data) {
        Log.d(TAG, "deliverResult");
        super.deliverResult(data);
    }

    private int getCountGroupItems(int groupId){
        String ORDER = null;
        if(mSettings.getNameAlt() == 0){
            ORDER = Data.DISPLAY_NAME;
        } else if(mSettings.getNameAlt() == 1){
            ORDER = Data.DISPLAY_NAME_ALTERNATIVE;
        }
        Cursor mCounterCur = mContentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.MIMETYPE + "= '" + GroupMembership.CONTENT_ITEM_TYPE +
                        "' AND " + GroupMembership.GROUP_SOURCE_ID + " IS NULL AND " +
                        GroupMembership.GROUP_ROW_ID + "= " + groupId,
                null,
                ORDER
        );
        ArrayList<BaseObject> mContacts = new ArrayList<>();
        if(mCounterCur != null) {
            while(mCounterCur.moveToNext()){
                ContactListItem mItem = new ContactListItem();
                mItem.setCID(mCounterCur.getLong(mCounterCur.getColumnIndex(Data.CONTACT_ID)));
                mItem.setDisplayName(mCounterCur.getString(mCounterCur.getColumnIndex(Data.DISPLAY_NAME)));
                mItem.setDisplayNameAlternative(mCounterCur.getString(mCounterCur.getColumnIndex(Data.DISPLAY_NAME_ALTERNATIVE)));
                mItem.setPhoneBookLabel(mCounterCur.getString(mCounterCur.getColumnIndex("phonebook_label")));
                mItem.setPhoneBookLabelAlternative(mCounterCur.getString(mCounterCur.getColumnIndex("phonebook_label_alt")));
                mItem.setPhotoUri(mCounterCur.getString(mCounterCur.getColumnIndex(Data.PHOTO_URI)));
                mItem.setThumbPhotoUri(mCounterCur.getString(mCounterCur.getColumnIndex(Data.PHOTO_THUMBNAIL_URI)));
                mContacts.add(mItem);
            }
        }
        mCounterCur.close();
        if(mContacts.size() > 0) {
            mRecords.put(groupId, mContacts);
        }
        return mContacts.size();
    }

    private Drawable getAccountImage(String accountType){
        for(AccountData mAccount : mAccounts){
            if(mAccount.getType().equals(accountType)){
                return mAccount.getIcon();
            }
        }
        return null;
    }
}
