package ru.mdsps.contacts.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.Group;
import ru.mdsps.contacts.interfaces.OnExpandListener;

public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Group mRecord;
    private int mPosition;
    private OnExpandListener mListener;
    private ImageView mAccountIcon, mButtonImage;
    private TextView mTitle, mDesc;
    private FrameLayout mButton;

    public GroupViewHolder(View itemView, OnExpandListener listener) {
        super(itemView);

        mListener = listener;
        mAccountIcon = (ImageView) itemView.findViewById(R.id.item_icon_image);
        mButtonImage = (ImageView) itemView.findViewById(R.id.item_button_image);
        mTitle = (TextView) itemView.findViewById(R.id.item_name);
        mDesc = (TextView) itemView.findViewById(R.id.item_desc);
        mButton = (FrameLayout) itemView.findViewById(R.id.item_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(!mRecord.isExpanded()){ // Свернутое состояние
            mRecord.setExpanded(true);
            mButtonImage.setImageResource(R.drawable.vector_ic_expand_less_grey);
            mListener.onExpandMore((int)v.getTag(), mRecord.getGroupId(), mRecord.getCountItem());
        } else { // Развернутое состояние
            mRecord.setExpanded(false);
            mButtonImage.setImageResource(R.drawable.vector_ic_expand_more_grey);
            mListener.onExpandLess((int)v.getTag(), mRecord.getGroupId(), mRecord.getCountItem());
        }
    }

    public void setupHolder(BaseObject record, int position){
        mRecord = (Group) record;
        mAccountIcon.setImageDrawable(mRecord.getImage());
        mTitle.setText(mRecord.getTitle());
        mDesc.setText(itemView.getResources()
                .getQuantityString(R.plurals.group_contacts_counter, mRecord.getCountItem(), mRecord.getCountItem()));
        if(mRecord.isExpanded()){
            mButtonImage.setImageResource(R.drawable.vector_ic_expand_less_grey);
        } else {
            mButtonImage.setImageResource(R.drawable.vector_ic_expand_more_grey);
        }
        mButton.setTag(position);
    }
}
