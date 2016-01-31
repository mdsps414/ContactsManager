package ru.mdsps.contacts.core.settings;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ru.mdsps.contacts.AppContacts;
import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.model.AccountData;
import ru.mdsps.contacts.core.utility.AppUtility;

public class SettingsAccountsAdapter extends BaseAdapter implements View.OnClickListener{

    private ArrayList<String> mSelectedItem;
    private ArrayList<AccountData> mBaseItem;
    private ArrayList<String> mPhoneBookItem;
    private Context mContext;

    public SettingsAccountsAdapter(ArrayList<String> selected){
        mSelectedItem = selected;
        mBaseItem = AppUtility.getAccountList();
        mPhoneBookItem = AppUtility.getPhoneBookAccountList();
        mContext = AppContacts.getContext();
    }


    @Override
    public int getCount() {
        return mBaseItem.size();
    }

    @Override
    public AccountData getItem(int position) {
        return mBaseItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        if(convertView == null){
            v = View.inflate(mContext, R.layout.settings_item_caontact_account, null);
        } else {
            v = convertView;
        }
        RelativeLayout acc_item = (RelativeLayout) v.findViewById(R.id.acc_item);
        CheckBox acc_check = (CheckBox) v.findViewById(R.id.acc_item_check);
        TextView acc_name = (TextView) v.findViewById(R.id.acc_item_name);
        TextView acc_desc = (TextView) v.findViewById(R.id.acc_item_desc);
        ImageView acc_icon = (ImageView) v.findViewById(R.id.acc_item_icon);

        // Данные
        AccountData item = getItem(position);
        acc_name.setText(item.getTypeLabel());
        acc_desc.setText(item.getName());
        acc_icon.setImageDrawable(item.getIcon());
        acc_check.setChecked(isChecked(item));

        if(!isPhoneBook(item)){
            acc_item.setEnabled(false);
            acc_check.setEnabled(false);
            acc_name.setEnabled(false);
            acc_desc.setEnabled(false);
            acc_icon.setEnabled(false);
        } else {
            acc_item.setEnabled(true);
            acc_check.setEnabled(true);
            acc_name.setEnabled(true);
            acc_desc.setEnabled(true);
            acc_icon.setEnabled(true);
        }

        acc_item.setTag(item);
        acc_item.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        AccountData item = (AccountData) v.getTag();
        CheckBox acc_check = (CheckBox) v.findViewById(R.id.acc_item_check);
        if(!isPhoneBook(item)){
            acc_check.setChecked(!acc_check.isChecked());
        }
    }

    private boolean isChecked(AccountData item){
        String mVal = item.getNameType();
        if(mSelectedItem != null && mSelectedItem.contains(mVal)){
            return true;
        }
        return false;
    }

    private boolean isPhoneBook(AccountData item){
        String mVal = item.getNameType();
        if(mPhoneBookItem != null && mPhoneBookItem.contains(mVal)){
            return true;
        }
        return false;
    }
}
