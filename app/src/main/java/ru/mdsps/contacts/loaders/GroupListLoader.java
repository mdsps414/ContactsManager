package ru.mdsps.contacts.loaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Groups;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.Group;
import ru.mdsps.contacts.settings.SettingsProvider;

public class GroupListLoader extends AsyncTaskLoader<ArrayList<BaseObject>> {

    private String TAG = "GroupListLoader";
    private ContentResolver mContentResolver;
    private SettingsProvider mSettings;

    public GroupListLoader(Context context) {
        super(context);
        mContentResolver = getContext().getContentResolver();
        mSettings = new SettingsProvider();
    }

    @Override
    public ArrayList<BaseObject> loadInBackground() {
        ArrayList<BaseObject> mRecords = new ArrayList<>();
        Uri URI;
        String[] PROJECTION;
        String SELECTION = null;
        String ORDER = null;

        URI = Groups.CONTENT_URI;
        PROJECTION = new String[]{
                Groups._ID,
                Groups.TITLE,
                Groups.ACCOUNT_NAME,
                Groups.ACCOUNT_TYPE
        };
        Cursor mGroupsCursor = mContentResolver.query(URI, PROJECTION, null, null, null);
        while(mGroupsCursor.moveToNext()){
            /*StringBuilder sb = new StringBuilder();
            for(int i = 0; i < mGroupsCursor.getColumnCount(); i++){
                sb.append(mGroupsCursor.getColumnName(i));
                sb.append(" = ");
                sb.append(mGroupsCursor.getString(i));
                sb.append("; ");
            }
            Log.w(TAG, sb.toString());*/
            Group mItem = new Group();
            mItem.setGroupId(mGroupsCursor.getInt(0));
            mItem.setTitle(mGroupsCursor.getString(1));
            String gId = mGroupsCursor.getString(0);
            int Count = getCountGroupItems(gId);
            mRecords.add(mItem);
        }
        mGroupsCursor.close();

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

    private int getCountGroupItems(String groupId){
        Cursor counter = mContentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.GroupMembership._COUNT},
                ContactsContract.CommonDataKinds.GroupMembership._ID + "= " + groupId,
                null,
                null
        );
        counter.moveToFirst();
        int val = counter.getInt(0);
        counter.close();

        return val;
    }
}
