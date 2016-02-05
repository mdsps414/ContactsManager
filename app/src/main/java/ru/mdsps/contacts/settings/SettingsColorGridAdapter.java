package ru.mdsps.contacts.settings;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.afollestad.materialdialogs.color.CircleView;

import ru.mdsps.contacts.AppContacts;


public class SettingsColorGridAdapter extends BaseAdapter {

    int[] mBaseColors;
    int mSelectedColor;
    int mSelectedColorIndex;
    OnElementClickListener mListener;

    public SettingsColorGridAdapter(int[] colorResource, int selectedColor, OnElementClickListener listener){
        mBaseColors = colorResource;
        mSelectedColor = selectedColor;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mBaseColors.length;
    }

    @Override
    public Object getItem(int position) {
        return mBaseColors[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int mCircleSize = AppContacts.getContext().getResources()
                .getDimensionPixelSize(com.afollestad.materialdialogs.commons.R.dimen.md_colorchooser_circlesize);
        if (convertView == null) {
            convertView = new CircleView(AppContacts.getContext());
            convertView.setLayoutParams(new GridView.LayoutParams(mCircleSize, mCircleSize));
        }
        CircleView child = (CircleView) convertView;
        final int color = mBaseColors[position];
        child.setBackgroundColor(color);
        if(color == mSelectedColor) {
            child.setSelected(true);
            mSelectedColorIndex = position;
        }
        child.setTag(color);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onElementClick(AppContacts.SETTINGS_LISTENER_COLOR, v);
            }
        });
        return convertView;
    }
}
