package com.example.adryel.newsapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class NewsRepository {
    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsRepository(Application application){
        NewsRoomDB db = NewsRoomDB.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        mAllNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    public void resyncDBwithAPI() { new syncDataBaseWithAPI(mNewsItemDao).execute(); }

    public LiveData<List<NewsItem>> getAllNewsItems() { return mNewsItemDao.loadAllNewsItems(); }

    private static class syncDataBaseWithAPI extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncNewsDao;

        syncDataBaseWithAPI(NewsItemDao dao) {
            mAsyncNewsDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(NetworkUtils.buildUrl());

                ArrayList<NewsItem> newNews = JsonUtils.parseNews(jsonResponse);
                mAsyncNewsDao.clearAll();

                for (NewsItem ni : newNews)
                    mAsyncNewsDao.insert(ni);

            } catch (Exception e) { e.printStackTrace(); }

            return null;
        }
    }

    public LiveData<List<NewsItem>> loadAllNewsItems() { return mAllNewsItems; }
}
