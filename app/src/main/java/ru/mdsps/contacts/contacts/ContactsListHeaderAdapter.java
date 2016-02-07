package ru.mdsps.contacts.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseObject;
import ru.mdsps.contacts.core.model.ContactListItem;
import ru.mdsps.contacts.core.views.stickyheader.StickyHeadersAdapter;
import ru.mdsps.contacts.settings.Settings;

public class ContactsListHeaderAdapter implements StickyHeadersAdapter<ContactsListHeaderAdapter.ViewHolder> {

    private ArrayList<BaseObject> mRecords;
    private Settings mSettings;

    public ContactsListHeaderAdapter(ArrayList<BaseObject> records){
        mRecords = records;
        mSettings = new Settings();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header_contact_list_alphabet, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ContactListItem mRecord = (ContactListItem) mRecords.get(position);
        switch(mSettings.getNameAlt()){
            case 0:
                viewHolder.letter.setText(mRecord.getPhoneBookLabel());
                break;
            case 1:
                viewHolder.letter.setText(mRecord.getPhoneBookLabelAlternative());
                break;
        }
    }

    @Override
    public long getHeaderId(int position) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView letter;

        public ViewHolder(View itemView) {
            super(itemView);
            letter = (TextView) itemView.findViewById(R.id.letter);
        }
    }
}
