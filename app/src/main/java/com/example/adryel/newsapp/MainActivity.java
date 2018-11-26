package com.example.adryel.newsapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.NewsOnClickHandler {

    private ProgressBar mLoadingIndicator;
    private NewsRecyclerViewAdapter mNewsAdapter;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private NewsItemViewModel mNewsItemViewModel;

    private class NewsQueryTask extends AsyncTask<Void, Void, ArrayList<NewsItem>> {

        @Override
        protected ArrayList<NewsItem> doInBackground(Void... voids) {
//            URL requestUrl = NetworkUtils.buildUrl();
//
//            try {
//                String jsonResponse = NetworkUtils
//                        .getResponseFromHttpUrl(requestUrl);
//
//                return JsonUtils.parseNews(jsonResponse);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
            LiveData<List<NewsItem>> allNews = mNewsItemViewModel.getNews();
            return (ArrayList<NewsItem>) allNews.getValue();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> newsItems) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (newsItems != null) {
                showRecyclerView();
                mNewsAdapter.setNewsData(newsItems);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsItemViewModel.getNews().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                mNewsAdapter.setNewsData((ArrayList<NewsItem>) newsItems);
            }
        });

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

//        loadWeatherData();

    }

    @Override
    public void onClick(NewsItem item) {
        Uri webpage = Uri.parse(item.url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            Toast.makeText(this, "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            mNewsAdapter.setNewsData(null);
            loadWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadWeatherData() {
        showRecyclerView();
        mNewsItemViewModel.syncNews();
        new NewsQueryTask().execute();
    }

    private void showRecyclerView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
