package com.example.adryel.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Recycler View Adapter to fit a News Item into the recycler view
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder> {

    private ArrayList<NewsItem> mNewsData;
    private NewsOnClickHandler mOnClickHandler;

    /*
     * Interface to handle click events
     */
    public interface NewsOnClickHandler {
        void onClick(NewsItem item);
    }

    public NewsRecyclerViewAdapter (NewsOnClickHandler handler) {
        mOnClickHandler = handler;
    }

    /*
     * The holder for the various News Items that will be loaded into the Recycler View
     */
    public class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mNewsTextView;

        public NewsItemViewHolder(View view) {
            super(view);
            mNewsTextView = (TextView) view.findViewById(R.id.news_data);
            view.setOnClickListener(this);
        }

        /*
         * Retrieve the clicked News Item from the list of news and send it
         * to the Main Activity class to handle the browser intent
         */
        @Override
        public void onClick(View v) {
            NewsItem item = mNewsData.get(getAdapterPosition());
            mOnClickHandler.onClick(item);
        }
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder newsItemViewHolder, int position) {
        String currentNews = mNewsData.get(position).getCurrentNews();
        newsItemViewHolder.mNewsTextView.setText(currentNews);
    }

    @Override
    public int getItemCount() {
        return (mNewsData == null) ? 0 : mNewsData.size();
    }

    public void setNewsData(ArrayList<NewsItem> data){
        mNewsData = data;
        notifyDataSetChanged();
    }
}
