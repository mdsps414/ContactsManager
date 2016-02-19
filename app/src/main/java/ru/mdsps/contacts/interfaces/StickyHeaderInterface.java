package ru.mdsps.contacts.interfaces;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface StickyHeaderInterface<T extends RecyclerView.ViewHolder> {

    long getHeaderId(int position);
    T onCreateHeaderViewHolder(ViewGroup parent);
    void onBindHeaderViewHolder(T viewholder, int position);

}
