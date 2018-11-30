package com.example.adryel.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;


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
        private final TextView mNewsTitleTextView;
        private final TextView mNewsDescriptTextView;
        private final ImageView mNewsThumbnail;

        public NewsItemViewHolder(View view) {
            super(view);
            mNewsTitleTextView = (TextView) view.findViewById(R.id.news_title);
            mNewsDescriptTextView = (TextView) view.findViewById(R.id.news_description);
            mNewsThumbnail = (ImageView) view.findViewById(R.id.news_thumbnail);
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
    public void onBindViewHolder(@NonNull NewsItemViewHolder newsItemViewHolder, int position) {
        NewsItem currentNewsItem = mNewsData.get(position);

        String newsTitle = currentNewsItem.getTitle();
        String newsDescript = currentNewsItem.getDescription();
        newsItemViewHolder.mNewsTitleTextView.setText(newsTitle);
        newsItemViewHolder.mNewsDescriptTextView.setText(newsDescript);

        Log.d("AdapterClass", currentNewsItem.urlToImage);
        if(currentNewsItem.urlToImage != null){
            Picasso.get()
                    .load(currentNewsItem.urlToImage)
                    .into(newsItemViewHolder.mNewsThumbnail);
        }
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
