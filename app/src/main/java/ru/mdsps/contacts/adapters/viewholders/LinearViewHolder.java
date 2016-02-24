package ru.mdsps.contacts.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.ContactListItem;
import ru.mdsps.contacts.core.utility.AppUtility;
import ru.mdsps.contacts.core.utility.FileUtility;
import ru.mdsps.contacts.settings.SettingsProvider;

public class LinearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ContactListItem mRecord;
    public TextView mItemName, mItemDesc, mLetter;
    public CircleImageView mItemIcon;
    public FrameLayout mButton;

    public LinearViewHolder(View itemView) {
        super(itemView);

        mItemName = (TextView) itemView.findViewById(R.id.item_name);
        mItemDesc = (TextView) itemView.findViewById(R.id.item_desc);
        mLetter = (TextView) itemView.findViewById(R.id.item_icon_letter);
        mItemIcon = (CircleImageView) itemView.findViewById(R.id.item_icon_image);
        mButton = (FrameLayout) itemView.findViewById(R.id.item_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void setupHolder(BaseObject record){
        this.mRecord = (ContactListItem) record;

        mItemName.setText(AppUtility.selName(mRecord));
        if(mRecord.getMobileNumber() != null) {
            mItemDesc.setText(mRecord.getMobileNumber());
            mItemDesc.setVisibility(View.VISIBLE);
        } else {
            mItemDesc.setVisibility(View.GONE);
        }
        mLetter.setText(AppUtility.selLetter(mRecord));
        if(mRecord.getPhotoUri() != null){
            if(FileUtility.checkFile(String.valueOf(mRecord.getCID()))){
                Picasso.with(mItemIcon.getContext())
                        .load(FileUtility.getFile(String.valueOf(mRecord.getCID())))
                        .placeholder(R.mipmap.contact_default)
                        .error(R.mipmap.contact_default)
                        .into(mItemIcon);

            } else if(mRecord.getPhotoUri() != null){
                Picasso.with(mItemIcon.getContext())
                        .load(mRecord.getThumbPhotoUri())
                        .placeholder(R.mipmap.contact_default)
                        .error(R.mipmap.contact_default)
                        .into(mItemIcon);
            }
            mLetter.setVisibility(View.GONE);
        } else {
            mItemIcon.setImageBitmap(AppUtility.generateBitmap());
            mLetter.setVisibility(View.VISIBLE);
        }
        SettingsProvider mSettings = new SettingsProvider();
        if(!mSettings.showCallButton()){
            mButton.setVisibility(View.INVISIBLE);
        } else {
            mButton.setVisibility(View.VISIBLE);
        }

    }
}
