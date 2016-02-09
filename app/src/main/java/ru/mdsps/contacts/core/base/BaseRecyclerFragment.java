package ru.mdsps.contacts.core.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.mdsps.contacts.core.views.fastscroller.FastScrollRecyclerView;

import ru.mdsps.contacts.R;

public abstract class BaseRecyclerFragment extends Fragment {

    private View mRootView;
    private ViewStub mStub;
    private LinearLayout mLoading;
    private TextView mEmpty;
    private int mEmptyText;
    private RecyclerView mRecycler;
    private FastScrollRecyclerView mFastScroller;
    private boolean mShowFastScroller;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_contact_list, container, false);

        mStub = (ViewStub) mRootView.findViewById(R.id.stub_recyclerview);
        mLoading = (LinearLayout) mRootView.findViewById(R.id.progress_box);
        mEmpty = (TextView) mRootView.findViewById(R.id.empty_text);
        mEmpty.setText(getString(mEmptyText));

        if(mShowFastScroller){
            mStub.setLayoutResource(R.layout.include_fast_scroller);
            mStub.inflate();
            mFastScroller = (FastScrollRecyclerView) mRootView.findViewById(R.id.contacts_fastscroller);
        } else {
            mStub.setLayoutResource(R.layout.include_recycler_view);
            mStub.inflate();
            mRecycler = (RecyclerView) mRootView.findViewById(R.id.contacts_recycler);
        }

        return mRootView;
    }

    public void setEmptyText(int textResource){
        mEmptyText = textResource;
    }

    public void showFastScroller(boolean show){
        mShowFastScroller = show;
    }

    public void setupRecyclerView(Adapter adapter, LayoutManager layoutManager, ItemAnimator itemAnimator){
        if(adapter.getItemCount() > 0){
            if(!mShowFastScroller) {
                mRecycler.setLayoutManager(layoutManager);
                mRecycler.setItemAnimator(itemAnimator);
                mRecycler.setAdapter(adapter);
            } else {
                mFastScroller.setAdapter(adapter);
                mFastScroller.setLayoutManager(layoutManager);
                mFastScroller.setItemAnimator(itemAnimator);
            }
            mStub.setVisibility(View.VISIBLE);
            mLoading.setVisibility(View.GONE);
            mEmpty.setVisibility(View.GONE);
        } else {
            mStub.setVisibility(View.GONE);
            mLoading.setVisibility(View.GONE);
            mEmpty.setVisibility(View.VISIBLE);
        }

    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener){
        if(!mShowFastScroller) {
            mRecycler.addOnScrollListener(listener);
        } else {
            mFastScroller.addOnScrollListener(listener);
        }
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor){
        if(!mShowFastScroller) {
            mRecycler.addItemDecoration(decor);
        } else {
            mFastScroller.addItemDecoration(decor);
        }
    }

    public RecyclerView getRecycler(){
        if(!mShowFastScroller) {
            return mRecycler;
        } else {
            return mFastScroller;
        }
    }
}
