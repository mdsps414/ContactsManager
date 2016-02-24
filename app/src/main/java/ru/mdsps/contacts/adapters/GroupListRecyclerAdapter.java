package ru.mdsps.contacts.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.adapters.viewholders.GroupViewHolder;
import ru.mdsps.contacts.adapters.viewholders.LinearViewHolder;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.interfaces.OnExpandListener;
import ru.mdsps.contacts.settings.SettingsProvider;

public class GroupListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    ArrayList<BaseObject> mRecords;
    OnExpandListener mListener;
    SettingsProvider mSettings;

    public GroupListRecyclerAdapter(ArrayList<BaseObject> records, OnExpandListener listener){
        mRecords = records;
        mListener = listener;
        mSettings = new SettingsProvider();
    }

    @Override
    public int getItemViewType(int position){
        BaseObject mRecord = mRecords.get(position);
        return mRecord.getObjectType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch (viewType){
            case BaseObject.GROUP:
                mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_category_list, parent, false);
                return new GroupViewHolder(mView, mListener);

            case BaseObject.CONTACT_LIST_ITEM:
                mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_contact_list, parent, false);
                return new LinearViewHolder(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseObject mRecord = mRecords.get(position);
        int viewType = getItemViewType(position);
        switch(viewType){
            case BaseObject.GROUP:
                GroupViewHolder lhv = (GroupViewHolder) holder;
                lhv.setupHolder(mRecord, position);
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
}
