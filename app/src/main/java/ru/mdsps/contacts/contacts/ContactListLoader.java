package ru.mdsps.contacts.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Email;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.ContactListItem;
import ru.mdsps.contacts.settings.Settings;

public class ContactListLoader extends AsyncTaskLoader<ArrayList<BaseObject>> {

    private String TAG = "ContactsLoader";
    private String mFilter;
    private String[] mLetters;
    private ContentResolver mContentResolver;
    private Settings mSettings;

    public ContactListLoader(Context context, String filter) {
        super(context);

        mLetters = getContext().getResources().getStringArray(R.array.alfabetic_array);
        mContentResolver = getContext().getContentResolver();
        mFilter = filter;
        mSettings = new Settings();
    }

    @Override
    public ArrayList<BaseObject> loadInBackground() {
        ArrayList<BaseObject> mRecords = new ArrayList<>();
        ArrayList<BaseObject> mRec = new ArrayList<>();
        HashMap<String, ContactListItem> mContactsMap = new HashMap<>();
        ArrayList<String> mShowAccounts = mSettings.getShowAccounts();

        Uri URI = null;
        String[] PROJECTION = null;
        String SELECTION = null;
        String[] PARAMETER = null;
        String ORDER = null;

        // Получаем основные данные
        URI = Contacts.CONTENT_URI;
        PROJECTION = new String[]{
                Contacts._ID,
                Contacts.DISPLAY_NAME,
                Contacts.DISPLAY_NAME_ALTERNATIVE,
                "phonebook_label",
                "phonebook_label_alt",
                Contacts.PHOTO_URI,
                Contacts.PHOTO_THUMBNAIL_URI,
                Contacts.LOOKUP_KEY
        };
        if(mFilter != null){
            SELECTION = mFilter;
        }

        // Получаем список всех контактов из телефона
        Cursor mContactsCursor = mContentResolver.query(URI, PROJECTION, SELECTION, PARAMETER, ORDER);
        while(mContactsCursor.moveToNext()){
            ContactListItem mItem = new ContactListItem();
            mItem.setCID(mContactsCursor.getLong(0));
            mItem.setDisplayName(mContactsCursor.getString(1));
            mItem.setDisplayNameAlternative(mContactsCursor.getString(2));
            mItem.setPhoneBookLabel(mContactsCursor.getString(3));
            mItem.setPhoneBookLabelAlternative(mContactsCursor.getString(4));
            mItem.setPhotoUri(mContactsCursor.getString(5));
            mItem.setThumbPhotoUri(mContactsCursor.getString(6));
            String mLookup = mContactsCursor.getString(0);
            mContactsMap.put(mLookup, mItem);
        }
        mContactsCursor.close();

        // Получаем список всех мобильных телефонов по контактам
        URI = Phone.CONTENT_URI;
        PROJECTION = new String[]{
                Phone.CONTACT_ID,
                Phone.NUMBER
        };
        SELECTION = Phone.TYPE + " = " + Phone.TYPE_MOBILE;
        mContactsCursor = mContentResolver.query(URI, PROJECTION, SELECTION, PARAMETER, ORDER);
        while(mContactsCursor.moveToNext()){
            ContactListItem mItem = mContactsMap.get(mContactsCursor.getString(0));
            if(mItem != null){
                mItem.setMobileNumber(mContactsCursor.getString(1));
            }
        }
        mContactsCursor.close();

        // Получаем данные для сортировки контактов по аккаунтам
        URI = RawContacts.CONTENT_URI;
        PROJECTION = new String[]{
                RawContacts.CONTACT_ID,
                RawContacts.ACCOUNT_NAME,
                RawContacts.ACCOUNT_TYPE
        };
        String mAccountTypes = null;
        for(int i = 0; i < mShowAccounts.size(); i++){
            String mName = mShowAccounts.get(i);
            String[] mNameType = mName.split("@");
            if(mAccountTypes == null){
                mAccountTypes = "'" + mNameType[1] + "'";
            } else {
                mAccountTypes += ", '" + mNameType[1] + "'";
            }
        }

        SELECTION = RawContacts.DISPLAY_NAME_PRIMARY + " IS NOT NULL AND " + RawContacts.ACCOUNT_TYPE + " IN (" + mAccountTypes + ")";
        mContactsCursor = mContentResolver.query(URI, PROJECTION, SELECTION, PARAMETER, ORDER);
        while(mContactsCursor.moveToNext()){
            ContactListItem mItem = mContactsMap.get(mContactsCursor.getString(0));
            if(mItem != null){
                mRec.add(mItem);
            }
        }
        mContactsCursor.close();

        // Сортируем полученный список
        Comparator<BaseObject> mComparator = new Comparator<BaseObject>() {
            @Override
            public int compare(BaseObject lhs, BaseObject rhs) {
                return lhs.getItemPrimaryLabel().compareTo(rhs.getItemPrimaryLabel());
            }
        };

        Collections.sort(mRec, mComparator);

        // Раскладываем список согласно алфавита
        for(int i = 0; i < mLetters.length; i++){
            boolean mFirst = true;
            for(BaseObject mRecord : mRec){
                ContactListItem mItem = (ContactListItem)mRecord;
                if(mSettings.getNameAlt() == 0){
                    if(mItem.getPhoneBookLabel().equals(mLetters[i])){
                        if(mFirst){
                            mItem.setFirst(mFirst);
                            mFirst = false;
                        } else {
                            mItem.setFirst(mFirst);
                        }
                        mRecords.add(mItem);
                    }
                } else if(mSettings.getNameAlt() == 1){
                    if(mItem.getPhoneBookLabelAlternative().equals(mLetters[i])){
                        if(mFirst){
                            mItem.setFirst(mFirst);
                            mFirst = false;
                        } else {
                            mItem.setFirst(mFirst);
                        }
                        mRecords.add(mItem);
                    }
                }
            }
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
    public void deliverResult(ArrayList<BaseObject> data) {
        Log.d(TAG, "deliverResult");
        super.deliverResult(data);
    }
}
