package ru.mdsps.contacts.fragments;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;

import java.util.ArrayList;

import ru.mdsps.contacts.adapters.FavoritesListRecyclerAdapter;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.base.BaseRecyclerFragment;
import ru.mdsps.contacts.loaders.FavoritesListLoader;
import ru.mdsps.contacts.settings.SettingsProvider;

public class FavoritesListFragment extends BaseRecyclerFragment implements
        LoaderManager.LoaderCallbacks<ArrayList<BaseObject>>{

    final int CNN_FAVORITES_LIST_LOADER = 1;

    FavoritesListRecyclerAdapter mAdapter;

    public FavoritesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getSupportLoaderManager().initLoader(CNN_FAVORITES_LIST_LOADER, null, this);
    }


    @Override
    public Loader<ArrayList<BaseObject>> onCreateLoader(int id, Bundle args) {
        Loader<ArrayList<BaseObject>> mLoader = new FavoritesListLoader(getContext(), null);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<BaseObject>> loader, ArrayList<BaseObject> data) {
        if(loader.getId() == CNN_FAVORITES_LIST_LOADER){
            SettingsProvider mSettings = new SettingsProvider();
            mAdapter = new FavoritesListRecyclerAdapter(data);
            getRecyclerView().setLayoutManager(new GridLayoutManager(getActivity(), 2));
            getRecyclerView().setItemAnimator(new DefaultItemAnimator());
            getRecyclerView().setPadding(14, 0, 14, 0);
            setupRecyclerView(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<BaseObject>> loader) {

    }
}
