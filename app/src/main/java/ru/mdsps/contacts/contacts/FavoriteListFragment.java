package ru.mdsps.contacts.contacts;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.base.BaseRecyclerFragment;


public class FavoriteListFragment extends BaseRecyclerFragment implements
        LoaderManager.LoaderCallbacks<ArrayList<BaseObject>>{

    FavoritesListRecyclerAdapter adapter;

    public FavoriteListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getSupportLoaderManager().initLoader(AppContacts.CNN_FAVORITES_LIST_LOADER, null, this);
    }

    @Override
    public Loader<ArrayList<BaseObject>> onCreateLoader(int id, Bundle args) {
        String mFilter = ContactsContract.Contacts.STARRED + " = 1";
        Loader<ArrayList<BaseObject>> mLoader = new ContactListLoader(getContext(), mFilter);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<BaseObject>> loader, ArrayList<BaseObject> data) {
        if(loader.getId() == AppContacts.CNN_FAVORITES_LIST_LOADER){
            adapter = new FavoritesListRecyclerAdapter(data);
            RecyclerView.LayoutManager mGridManager = new GridLayoutManager(getActivity(),2);
            RecyclerView.ItemAnimator mItemAnimator = new DefaultItemAnimator();

            setupRecyclerView(adapter, mGridManager, mItemAnimator);
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
