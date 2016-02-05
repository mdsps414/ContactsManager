package ru.mdsps.contacts.contacts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.base.BaseRecyclerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends BaseRecyclerFragment implements
        LoaderManager.LoaderCallbacks<ArrayList<BaseObject>>{


    public ContactListFragment() {
        // Required empty public constructor
    }


    @Override
    public Loader<ArrayList<BaseObject>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<BaseObject>> loader, ArrayList<BaseObject> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<BaseObject>> loader) {

    }
}
