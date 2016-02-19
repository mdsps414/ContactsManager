package ru.mdsps.contacts.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.ContactListHeader;

public class LinearHeaderViewHolder extends RecyclerView.ViewHolder{

    TextView mHeaderLetter;
    ContactListHeader mRecord;

    public LinearHeaderViewHolder(View itemView) {
        super(itemView);

        mHeaderLetter = (TextView) itemView.findViewById(R.id.header_text);
    }

    public void setupHolder(BaseObject record) {
        this.mRecord = (ContactListHeader) record;

        mHeaderLetter.setText(mRecord.getHolderText());
    }
}
