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

public class SettingsAccountsAdapter extends BaseAdapter{

    private ArrayList<String> mSelectedItem;
    private ArrayList<AccountData> mBaseItem;
    private OnElementClickListener mListener;
    private Context mContext;

    public SettingsAccountsAdapter(ArrayList<String> selected, OnElementClickListener listener){
        mSelectedItem = selected;
        mBaseItem = AppUtility.getAccountList();
        mContext = AppContacts.getContext();
        mListener = listener;
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

        acc_item.setTag(item);
        acc_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox acc_check = (CheckBox) v.findViewById(R.id.acc_item_check);
                acc_check.setChecked(!acc_check.isChecked());
                if(acc_check.isChecked()) {
                    mListener.onElementClick(AppContacts.SETTINGS_LISTENER_ACCOUNT_SHOW, v);
                } else {
                    mListener.onElementClick(AppContacts.SETTINGS_LISTENER_ACCOUNT_HIDE, v);
                }
            }
        });

        return v;
    }

    private boolean isChecked(AccountData item){
        String mVal = item.getNameType();
        if(mSelectedItem != null && mSelectedItem.contains(mVal)){
            return true;
        }
        return false;
    }
}
