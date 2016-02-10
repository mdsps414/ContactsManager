package ru.mdsps.contacts.contacts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.ContactListItem;
import ru.mdsps.contacts.core.utility.AppUtility;
import ru.mdsps.contacts.core.utility.FileUtility;
import ru.mdsps.contacts.core.views.CircleTextView;
import ru.mdsps.contacts.core.views.fastscroller.RecyclerViewFastScroller;
import ru.mdsps.contacts.settings.Settings;

public class ContactsListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements RecyclerViewFastScroller.BubbleTextGetter {

    ArrayList<BaseObject> mRecords;
    Settings mSettings;
    int mViewMode = 0;

    public ContactsListRecyclerAdapter(ArrayList<BaseObject> records){
        mRecords = records;
        mSettings = new Settings();
        mViewMode = mSettings.getItemType();

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

        switch(viewType){
            case 0: // Нет картинки
                switch(mViewMode) {
                    case 0:
                        mView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_contact_list_alphabet_without_image, parent, false);
                        return new AlphabetNoImageViewHolder(mView);
                    case 1:
                        mView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_contact_list_without_image, parent, false);
                        return new NoImageViewHolder(mView);
                    }
                break;
            case 1: // Есть картинка
                switch(mViewMode) {
                    case 0:
                        mView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_contact_list_alphabet_with_image, parent, false);
                        return new AlphabetImageViewHolder(mView);
                    case 1:
                        mView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_contact_list_with_image, parent, false);
                        return new ImageViewHolder(mView);
                }
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactListItem mRecord = (ContactListItem) mRecords.get(position);
        int viewType = getItemViewType(position);
        switch(viewType){
            case 0: // Нет картинки
                switch(mViewMode){
                    case 0: // Алфавитный
                        AlphabetNoImageViewHolder h0 = (AlphabetNoImageViewHolder) holder;
                        h0.setRecord(mRecord);
                        h0.mTextName.setText(selName(mRecord));
                        h0.mTextDesc.setText(mRecord.getMobileNumber());
                        h0.mPhoto.setText(selLetter(mRecord));
                        h0.mPhoto.setBackgroundColor(AppUtility.getRandomColor());
                        break;
                    case 1: // Не алфавитный
                        NoImageViewHolder h1 = (NoImageViewHolder) holder;
                        h1.setRecord(mRecord);
                        h1.mTextName.setText(selName(mRecord));
                        h1.mTextDesc.setText(mRecord.getMobileNumber());
                        h1.mPhoto.setText(selLetter(mRecord));
                        h1.mPhoto.setBackgroundColor(AppUtility.getRandomColor());
                        if(mSettings.showCallButton()){
                            h1.mCallButton.setVisibility(View.VISIBLE);
                        } else {
                            h1.mCallButton.setVisibility(View.INVISIBLE);
                        }

                }
                break;
            case 1: // Есть картинка
                switch(mViewMode){
                    case 0: // Алфавитный
                        AlphabetImageViewHolder h0 = (AlphabetImageViewHolder) holder;
                        h0.setRecord(mRecord);
                        h0.mTextName.setText(selName(mRecord));
                        h0.mTextDesc.setText(mRecord.getMobileNumber());
                        // Добавление картинки
                        if(FileUtility.checkFile(String.valueOf(mRecord.getCID()))){
                            Picasso.with(h0.mPhoto.getContext())
                                    .load(FileUtility.getFile(String.valueOf(mRecord.getCID())))
                                    .placeholder(R.mipmap.contact_default)
                                    .error(R.mipmap.contact_default)
                                    .into(h0.mPhoto);

                        } else if(mRecord.getPhotoUri() != null){
                            Picasso.with(h0.mPhoto.getContext())
                                    .load(mRecord.getPhotoUri())
                                    .placeholder(R.mipmap.contact_default)
                                    .error(R.mipmap.contact_default)
                                    .into(h0.mPhoto);
                        }
                        break;
                    case 1: // Не алфавитный
                        ImageViewHolder h1 = (ImageViewHolder) holder;
                        h1.setRecord(mRecord);
                        h1.mTextName.setText(selName(mRecord));
                        h1.mTextDesc.setText(mRecord.getMobileNumber());
                        // Добавление картинки
                        if(mSettings.showCallButton()){
                            h1.mCallButton.setVisibility(View.VISIBLE);
                        } else {
                            h1.mCallButton.setVisibility(View.INVISIBLE);
                        }
                        // Добавление картинки
                        if(FileUtility.checkFile(String.valueOf(mRecord.getCID()))){
                            Picasso.with(h1.mPhoto.getContext())
                                    .load(FileUtility.getFile(String.valueOf(mRecord.getCID())))
                                    .placeholder(R.mipmap.contact_default)
                                    .error(R.mipmap.contact_default)
                                    .into(h1.mPhoto);

                        } else if(mRecord.getPhotoUri() != null){
                            Picasso.with(h1.mPhoto.getContext())
                                    .load(mRecord.getPhotoUri())
                                    .placeholder(R.mipmap.contact_default)
                                    .error(R.mipmap.contact_default)
                                    .into(h1.mPhoto);
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }


    @Override
    public String getTextToShowInBubble(int pos) {
        ContactListItem mRecord = (ContactListItem) mRecords.get(pos);
        return selLetter(mRecord);
    }


    class AlphabetImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTextName, mTextDesc;
        CircleImageView mPhoto;
        RelativeLayout mContactButton;
        ContactListItem mRecord;

        public AlphabetImageViewHolder(View itemView) {
            super(itemView);

            mTextName = (TextView) itemView.findViewById(R.id.item_name_label);
            mTextDesc = (TextView) itemView.findViewById(R.id.item_desc_label);
            mPhoto = (CircleImageView) itemView.findViewById(R.id.item_image);
            mContactButton = (RelativeLayout) itemView.findViewById(R.id.contact_item);
            mContactButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void setRecord(ContactListItem item){
            mRecord = item;
        }
    }

    class AlphabetNoImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTextName, mTextDesc;
        CircleTextView mPhoto;
        RelativeLayout mContactButton;
        ContactListItem mRecord;

        public AlphabetNoImageViewHolder(View itemView) {
            super(itemView);

            mTextName = (TextView) itemView.findViewById(R.id.item_name_label);
            mTextDesc = (TextView) itemView.findViewById(R.id.item_desc_label);
            mPhoto = (CircleTextView) itemView.findViewById(R.id.item_image_circle);
            mContactButton = (RelativeLayout) itemView.findViewById(R.id.contact_item);
            mContactButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void setRecord(ContactListItem item){
            mRecord = item;
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTextName, mTextDesc;
        CircleImageView mPhoto;
        LinearLayout mContactButton;
        FrameLayout mCallButton;
        ContactListItem mRecord;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mTextName = (TextView) itemView.findViewById(R.id.item_name_label);
            mTextDesc = (TextView) itemView.findViewById(R.id.item_desc_label);
            mPhoto = (CircleImageView) itemView.findViewById(R.id.item_image);
            mContactButton = (LinearLayout) itemView.findViewById(R.id.item_contact_button);
            mContactButton.setOnClickListener(this);
            mCallButton = (FrameLayout) itemView.findViewById(R.id.item_call_button);
            mCallButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.item_contact_button:
                    break;
                case R.id.item_call_button:
                    break;
            }
        }

        public void setRecord(ContactListItem item){
            mRecord = item;
        }
    }

    class NoImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTextName, mTextDesc;
        CircleTextView mPhoto;
        LinearLayout mContactButton;
        FrameLayout mCallButton;
        ContactListItem mRecord;

        public NoImageViewHolder(View itemView) {
            super(itemView);

            mTextName = (TextView) itemView.findViewById(R.id.item_name_label);
            mTextDesc = (TextView) itemView.findViewById(R.id.item_desc_label);
            mPhoto = (CircleTextView) itemView.findViewById(R.id.item_image_circle);
            mContactButton = (LinearLayout) itemView.findViewById(R.id.item_contact_button);
            mContactButton.setOnClickListener(this);
            mCallButton = (FrameLayout) itemView.findViewById(R.id.item_call_button);
            mCallButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.item_contact_button:
                    break;
                case R.id.item_call_button:
                    break;
            }
        }

        public void setRecord(ContactListItem item){
            mRecord = item;
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
