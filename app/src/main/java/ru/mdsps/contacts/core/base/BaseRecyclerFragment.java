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
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.mdsps.contacts.R;

public abstract class BaseRecyclerFragment extends Fragment {

    private RecyclerView mRecycler;
    private LinearLayout mLoading;
    private TextView mEmpty;
    private String mEmptyText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        mRecycler = (RecyclerView) mRootView.findViewById(R.id.contacts_recycler);
        mLoading = (LinearLayout) mRootView.findViewById(R.id.progress_box);
        mEmpty = (TextView) mRootView.findViewById(R.id.empty_text);
        mEmpty.setText(mEmptyText);

        return mRootView;
    }

    public void setEmptyText(String text){
        mEmptyText = text;
    }

    public void setEmptyText(int textResource){
        mEmptyText = getString(textResource);
    }

    public void setupRecyclerView(Adapter adapter, LayoutManager layoutManager, ItemAnimator itemAnimator){
        if(adapter.getItemCount() > 0){
            mRecycler.setLayoutManager(layoutManager);
            mRecycler.setItemAnimator(itemAnimator);
            mLoading.setVisibility(View.GONE);
            mEmpty.setVisibility(View.GONE);
            mRecycler.setAdapter(adapter);
        } else {
            mRecycler.setVisibility(View.GONE);
            mLoading.setVisibility(View.GONE);
            mEmpty.setVisibility(View.VISIBLE);

        }

    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener){
        mRecycler.addOnScrollListener(listener);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor){
        mRecycler.addItemDecoration(decor);
    }

    public RecyclerView getRecycler(){
        return mRecycler;
    }
}
