package ru.mdsps.contacts.core.views.stickyheader;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface StickyHeadersAdapter<HeaderViewHolder extends RecyclerView.ViewHolder> {

    HeaderViewHolder onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(HeaderViewHolder headerViewHolder, int position);

    long getHeaderId(int position);
}
