package ru.mdsps.contacts.contacts;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.base.BaseRecyclerFragment;
import ru.mdsps.contacts.core.views.stickyheader.StickyHeadersBuilder;
import ru.mdsps.contacts.core.views.stickyheader.StickyHeadersItemDecoration;
import ru.mdsps.contacts.settings.Settings;


public class ContactListFragment extends BaseRecyclerFragment implements
        LoaderManager.LoaderCallbacks<ArrayList<BaseObject>>{

    ContactsListRecyclerAdapter adapter;

    public ContactListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getSupportLoaderManager().initLoader(AppContacts.CNN_CONTACTS_LIST_LOADER, null, this);
    }


    @Override
    public Loader<ArrayList<BaseObject>> onCreateLoader(int id, Bundle args) {
        Loader<ArrayList<BaseObject>> mLoader = new ContactListLoader(getContext(), null);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<BaseObject>> loader, ArrayList<BaseObject> data) {
        if(loader.getId() == AppContacts.CNN_CONTACTS_LIST_LOADER){
            Settings mSettings = new Settings();
            adapter = new ContactsListRecyclerAdapter(data);
            LayoutManager mLinearManager = new LinearLayoutManager(getActivity());
            ItemAnimator mItemAnimator = new DefaultItemAnimator();
            if(mSettings.getItemType() == 0){
                StickyHeadersItemDecoration overlay = new StickyHeadersBuilder()
                        .setAdapter(adapter)
                        .setRecyclerView(getRecycler())
                        .setStickyHeadersAdapter(new ContactsListHeaderAdapter(data), true)
                        .build();
                addItemDecoration(overlay);
            }

            setupRecyclerView(adapter, mLinearManager, mItemAnimator);
            addOnScrollListener(new MyOnScrollListener());
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<BaseObject>> loader) {

    }

    private class MyOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState){

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy){

        }
    }


}
