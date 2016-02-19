package ru.mdsps.contacts.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.adapters.viewholders.LinearHeaderViewHolder;
import ru.mdsps.contacts.adapters.viewholders.LinearViewHolder;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.ContactListHeader;
import ru.mdsps.contacts.core.model.ContactListItem;
import ru.mdsps.contacts.core.utility.AppUtility;
import ru.mdsps.contacts.core.views.fastscroller.FastScrollRecyclerView;
import ru.mdsps.contacts.interfaces.StickyHeaderInterface;
import ru.mdsps.contacts.settings.SettingsProvider;

public class ContactsListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        StickyHeaderInterface<LinearHeaderViewHolder>, FastScrollRecyclerView.SectionedAdapter{

    ArrayList<BaseObject> mRecords;
    SettingsProvider mSettings;
    int mViewMode = 0;

    public ContactsListRecyclerAdapter(ArrayList<BaseObject> records){
        mRecords = records;
        mSettings = new SettingsProvider();
        mViewMode = mSettings.getItemType();
    }

    @Override
    public int getItemViewType(int position){
        BaseObject mRecord = mRecords.get(position);
        return mRecord.getObjectType();
    }

    @Override
    public long getItemId(int position) {
        BaseObject mRecord = mRecords.get(position);
        String mLetter = null;
        switch(mRecord.getObjectType()){
            case BaseObject.ALPHABET_HEADER:
                ContactListHeader mRec = (ContactListHeader) mRecord;
                mLetter = mRec.getHolderText();
                break;
            case BaseObject.CONTACT_LIST_ITEM:
                ContactListItem mRes = (ContactListItem) mRecord;
                mLetter = AppUtility.selLetter(mRes);
                break;
        }

        return mLetter != null ? mLetter.charAt(0) : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = null;
        switch (viewType){
            case BaseObject.ALPHABET_HEADER:
                mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_contact_list_alphabet_header, parent, false);
                return new LinearHeaderViewHolder(mView);

            case BaseObject.CONTACT_LIST_ITEM:
                switch(mViewMode){
                    case 0:
                        mView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_contact_list_alphabet, parent, false);
                        break;
                    case 1:
                        mView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_contact_list, parent, false);
                        break;
                }

                return new LinearViewHolder(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseObject mRecord = mRecords.get(position);
        int viewType = getItemViewType(position);
        switch(viewType){
            case BaseObject.ALPHABET_HEADER:
                LinearHeaderViewHolder lhv = (LinearHeaderViewHolder) holder;
                lhv.setupHolder(mRecord);
                break;
            case BaseObject.CONTACT_LIST_ITEM:
                LinearViewHolder lh = (LinearViewHolder) holder;
                lh.setupHolder(mRecord);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    @Override
    public long getHeaderId(int position) {
        return getItemId(position);
    }

    @Override
    public LinearHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact_list_alphabet_header, parent, false);
        return new LinearHeaderViewHolder(mView);
    }

    @Override
    public void onBindHeaderViewHolder(LinearHeaderViewHolder viewholder, int position) {
        ContactListItem mItem = (ContactListItem) mRecords.get(position);
        ContactListHeader mHeader = new ContactListHeader();
        mHeader.setHolderText(AppUtility.selLetter(mItem));
        viewholder.setupHolder(mHeader);
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        ContactListItem mRecord = (ContactListItem) mRecords.get(position);
        return AppUtility.selLetter(mRecord);
    }
}
