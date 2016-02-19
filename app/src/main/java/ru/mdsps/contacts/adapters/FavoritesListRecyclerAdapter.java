package ru.mdsps.contacts.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.adapters.viewholders.FavoriteViewHolder;
import ru.mdsps.contacts.adapters.viewholders.LinearHeaderViewHolder;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.ContactListItem;
import ru.mdsps.contacts.core.utility.AppUtility;
import ru.mdsps.contacts.settings.SettingsProvider;

public class FavoritesListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<BaseObject> mRecords;
    SettingsProvider mSettings;

    public FavoritesListRecyclerAdapter(ArrayList<BaseObject> records) {
        mRecords = records;
        mSettings = new SettingsProvider();
    }

    @Override
    public int getItemViewType(int position){
        BaseObject mRecord = mRecords.get(position);
        return mRecord.getObjectType();
    }

    @Override
    public long getItemId(int position) {
        BaseObject mRecord = mRecords.get(position);
        if(mRecord instanceof ContactListItem) {
            ContactListItem mRec = (ContactListItem) mRecord;
            String mLetter = AppUtility.selLetter(mRec);
            return mLetter != null ? mLetter.charAt(0) : 0;
        } else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch (viewType){
            case BaseObject.ALPHABET_HEADER:
                mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_contact_list_alphabet_header, parent, false);
                return new LinearHeaderViewHolder(mView);

            case BaseObject.CONTACT_LIST_ITEM:
                mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_favorite_list, parent, false);
                return new FavoriteViewHolder(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseObject mRecord = mRecords.get(position);
        int viewType = getItemViewType(position);
        switch(viewType){
            case BaseObject.ALPHABET_HEADER:
                break;
            case BaseObject.CONTACT_LIST_ITEM:
                FavoriteViewHolder lh = (FavoriteViewHolder) holder;
                lh.setupHolder(mRecord);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    private String selName(ContactListItem mItem){
        SettingsProvider mSettings = new SettingsProvider();
        if(mSettings.getNameAlt() == 0){
            return mItem.getDisplayName();
        } else if(mSettings.getNameAlt() == 1){
            return mItem.getDisplayNameAlternative();
        }
        return null;
    }

    private String selLetter(ContactListItem mItem){
        SettingsProvider mSettings = new SettingsProvider();
        if(mSettings.getNameAlt() == 0){
            return mItem.getPhoneBookLabel();
        } else if(mSettings.getNameAlt() == 1){
            return mItem.getPhoneBookLabelAlternative();
        }
        return null;
    }
}
