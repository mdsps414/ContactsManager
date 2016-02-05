package ru.mdsps.contacts.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;

public class ContactListLoader extends AsyncTaskLoader<ArrayList<BaseObject>> {

    private String TAG = "ContactsLoader";
    private String mFilter;
    private String[] mLetters;
    private HashMap<String, String> mGroupList = new HashMap<>();
    private ContentResolver mContentResolver;

    public ContactListLoader(Context context, String filter) {
        super(context);

        mLetters = getContext().getResources().getStringArray(R.array.alfabetic_array);
        mContentResolver = getContext().getContentResolver();
        mFilter = filter;
    }

    @Override
    public ArrayList<BaseObject> loadInBackground() {
        return null;
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
