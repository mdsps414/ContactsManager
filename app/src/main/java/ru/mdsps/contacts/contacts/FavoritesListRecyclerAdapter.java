package ru.mdsps.contacts.contacts;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.ContactListItem;
import ru.mdsps.contacts.core.utility.AppUtility;
import ru.mdsps.contacts.core.utility.FileUtility;
import ru.mdsps.contacts.settings.Settings;

public class FavoritesListRecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<BaseObject> mRecords;
    Settings mSettings;

    public FavoritesListRecyclerAdapter(ArrayList<BaseObject> records) {
        mRecords = records;
        mSettings = new Settings();

        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position){
        ContactListItem mContact = (ContactListItem) mRecords.get(position);
        if(mContact.getPhotoUri() != null){
            return 1;
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        ContactListItem mRecord = (ContactListItem) mRecords.get(position);
        String mLetter = null;
        switch(mSettings.getNameAlt()){
            case 0:
                mLetter = mRecord.getPhoneBookLabel();
                break;
            case 1:
                mLetter = mRecord.getPhoneBookLabelAlternative();
                break;
        }
        return mLetter.charAt(0);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch(viewType) {
            case 0:
                mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_favorite_list_without_image, parent, false);
                return new NoImageViewHolder(mView);
            case 1:
                mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_favorite_list_with_image, parent, false);
                return new ImageViewHolder(mView);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactListItem mRecord = (ContactListItem) mRecords.get(position);
        int viewType = getItemViewType(position);
        switch(viewType){
            case 0:
                NoImageViewHolder hni = (NoImageViewHolder) holder;
                hni.mIcon.setBackgroundColor(AppUtility.getRandomColor());
                hni.mLetter.setText(selLetter(mRecord));
                hni.mName.setText(selName(mRecord));
                hni.mDesc.setText(mRecord.getMobileNumber());
                break;
            case 1:
                ImageViewHolder hhi = (ImageViewHolder) holder;
                if(FileUtility.checkFile(String.valueOf(mRecord.getCID()))){
                    Picasso.with(hhi.mIcon.getContext())
                            .load(FileUtility.getFile(String.valueOf(mRecord.getCID())))
                            .placeholder(R.mipmap.contact_default)
                            .error(R.mipmap.contact_default)
                            .into(hhi.mIcon);

                } else if(mRecord.getPhotoUri() != null){
                    Picasso.with(hhi.mIcon.getContext())
                            .load(mRecord.getPhotoUri())
                            .placeholder(R.mipmap.contact_default)
                            .error(R.mipmap.contact_default)
                            .into(hhi.mIcon);
                }
                hhi.mName.setText(selName(mRecord));
                hhi.mDesc.setText(mRecord.getMobileNumber());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        FrameLayout mItem;
        ImageView mIcon;
        TextView mName, mDesc;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mItem = (FrameLayout) itemView.findViewById(R.id.favorite_item);
            mItem.setOnClickListener(this);
            mIcon = (ImageView) itemView.findViewById(R.id.favorite_item_image);
            mName = (TextView) itemView.findViewById(R.id.favorite_item_name);
            mDesc = (TextView) itemView.findViewById(R.id.favorite_item_desc);
        }

        @Override
        public void onClick(View v) {

        }
    }

    class NoImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        FrameLayout mItem, mIcon;
        TextView mName, mDesc, mLetter;

        public NoImageViewHolder(View itemView) {
            super(itemView);

            mItem = (FrameLayout) itemView.findViewById(R.id.favorite_item);
            mItem.setOnClickListener(this);
            mIcon = (FrameLayout) itemView.findViewById(R.id.favorite_item_image);
            mLetter = (TextView) itemView.findViewById(R.id.favorite_item_image_letter);
            mName = (TextView) itemView.findViewById(R.id.favorite_item_name);
            mDesc = (TextView) itemView.findViewById(R.id.favorite_item_desc);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private String selName(ContactListItem mItem){
        Settings mSettings = new Settings();
        if(mSettings.getNameAlt() == 0){
            return mItem.getDisplayName();
        } else if(mSettings.getNameAlt() == 1){
            return mItem.getDisplayNameAlternative();
        }
        return null;
    }

    private String selLetter(ContactListItem mItem){
        Settings mSettings = new Settings();
        if(mSettings.getNameAlt() == 0){
            return mItem.getPhoneBookLabel();
        } else if(mSettings.getNameAlt() == 1){
            return mItem.getPhoneBookLabelAlternative();
        }
        return null;
    }

}
