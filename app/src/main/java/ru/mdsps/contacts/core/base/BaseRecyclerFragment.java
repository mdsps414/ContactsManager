package ru.mdsps.contacts.core.base;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.views.fastscroller.FastScrollRecyclerView;

public abstract class BaseRecyclerFragment extends Fragment {

    private View mRootView;
    private ViewStub mRecyclerStub;
    private RecyclerView mRecyclerView;
    private FastScrollRecyclerView mFastScroller;
    private TextView mEmptyView;
    private LinearLayout mProgressBox;
    private int mEmptyTextResource;
    private boolean mShowFastScroller;

    public BaseRecyclerFragment() {
        // Required empty public constructor
    }

    public void setEmptyText(int textResource){
        mEmptyTextResource = textResource;
    }

    public void setShowFastScroller(boolean show){
        mShowFastScroller = show;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.base_fragment_recycler, container, false);
        mRecyclerStub = (ViewStub) mRootView.findViewById(R.id.recycler_stub);
        mEmptyView = (TextView) mRootView.findViewById(R.id.empty);
        mEmptyView.setText(getString(mEmptyTextResource));
        mProgressBox = (LinearLayout) mRootView.findViewById(R.id.progress_box);
        if(!mShowFastScroller){
            mRecyclerStub.setLayoutResource(R.layout.stub_fragment_recycler_only);
            mRecyclerStub.inflate();
            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler);
        } else {
            mRecyclerStub.setLayoutResource(R.layout.stub_fragment_fastscroller_only);
            mRecyclerStub.inflate();
            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler);
        }

        return mRootView;
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public void setupRecyclerView(RecyclerView.Adapter adapter){
        if(adapter.getItemCount() > 0){
            mRecyclerView.setAdapter(adapter);
            mRecyclerStub.setVisibility(View.VISIBLE);
            mProgressBox.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
        } else {
            mRecyclerStub.setVisibility(View.GONE);
            mProgressBox.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }





}
