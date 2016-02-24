package ru.mdsps.contacts.fragments;


import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mdsps.contacts.adapters.ContactsListRecyclerAdapter;
import ru.mdsps.contacts.adapters.GroupListRecyclerAdapter;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.base.BaseRecyclerFragment;
import ru.mdsps.contacts.core.views.StickyHeaderDecoration;
import ru.mdsps.contacts.interfaces.OnExpandListener;
import ru.mdsps.contacts.loaders.ContactListLoader;
import ru.mdsps.contacts.loaders.GroupListLoader;
import ru.mdsps.contacts.settings.SettingsProvider;

public class GroupsListFragment extends BaseRecyclerFragment implements
        LoaderManager.LoaderCallbacks<HashMap<Integer, Object>>, OnExpandListener{

    final int CNN_GROUP_LIST_LOADER = 3;
    private OnExpandListener mListener;
    HashMap<Integer, Object> mData;
    ArrayList<BaseObject> mGroupsList;
    private GroupListRecyclerAdapter mAdapter;


    public GroupsListFragment() {
        // Required empty public constructor
        mListener = this;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getSupportLoaderManager().initLoader(CNN_GROUP_LIST_LOADER, null, this);
    }


    @Override
    public Loader<HashMap<Integer, Object>> onCreateLoader(int id, Bundle args) {
        Loader<HashMap<Integer, Object>> mLoader = new GroupListLoader(getContext());
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<HashMap<Integer, Object>> loader, HashMap<Integer, Object> data) {
        if(loader.getId() == CNN_GROUP_LIST_LOADER){
            mData = data;
            mGroupsList = (ArrayList<BaseObject>) mData.get(-1);
            mAdapter = new GroupListRecyclerAdapter(mGroupsList, mListener);
            getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
            getRecyclerView().setItemAnimator(new DefaultItemAnimator());
            setupRecyclerView(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<HashMap<Integer, Object>> loader) {

    }

    @Override
    public void onExpandMore(int position, int groupId, int itemCount) {
        ArrayList<BaseObject> mExpander = (ArrayList<BaseObject>) mData.get(groupId);
        int mAddedPosition = position + 1;
        for(BaseObject mAddedItem : mExpander){
            mGroupsList.add(mAddedPosition, mAddedItem);
            mAdapter.notifyItemInserted(mAddedPosition);
        }
        //Toast.makeText(getActivity(), "ExpandMore" + groupId + " " + itemCount, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onExpandLess(int position, int groupId, int itemCount) {
        for(int i = 0; i < itemCount; i++){
            mGroupsList.remove(position + 1);
            mAdapter.notifyItemRemoved(position + 1);
        }
        //Toast.makeText(getActivity(), "ExpandLess" + groupId + " " + itemCount, Toast.LENGTH_SHORT).show();
    }
}
