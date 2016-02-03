package ru.mdsps.contacts.core.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class FillListView extends ListView {

    public FillListView(Context context) {
        super(context);
    }

    public FillListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FillListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
