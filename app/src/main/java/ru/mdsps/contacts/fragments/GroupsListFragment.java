package ru.mdsps.contacts.fragments;


import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import ru.mdsps.contacts.adapters.ContactsListRecyclerAdapter;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.base.BaseRecyclerFragment;
import ru.mdsps.contacts.core.views.StickyHeaderDecoration;
import ru.mdsps.contacts.loaders.ContactListLoader;
import ru.mdsps.contacts.loaders.GroupListLoader;
import ru.mdsps.contacts.settings.SettingsProvider;

public class GroupsListFragment extends BaseRecyclerFragment implements
        LoaderManager.LoaderCallbacks<ArrayList<BaseObject>>{

    final int CNN_GROUP_LIST_LOADER = 2;

    ContactsListRecyclerAdapter mAdapter;

    public GroupsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getSupportLoaderManager().initLoader(CNN_GROUP_LIST_LOADER, null, this);
    }


    @Override
    public Loader<ArrayList<BaseObject>> onCreateLoader(int id, Bundle args) {
        Loader<ArrayList<BaseObject>> mLoader = new GroupListLoader(getContext());
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<BaseObject>> loader, ArrayList<BaseObject> data) {
        if(loader.getId() == CNN_GROUP_LIST_LOADER){
            /*SettingsProvider mSettings = new SettingsProvider();
            mAdapter = new ContactsListRecyclerAdapter(data);
            StickyHeaderDecoration decor = new StickyHeaderDecoration(mAdapter, true);
            getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
            getRecyclerView().setItemAnimator(new DefaultItemAnimator());
            getRecyclerView().addItemDecoration(decor);
            setupRecyclerView(mAdapter);*/
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<BaseObject>> loader) {

    }
}
