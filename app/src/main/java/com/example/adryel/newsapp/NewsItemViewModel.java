package com.example.adryel.newsapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsRepository mRepository;
    private LiveData<List<NewsItem>> mNews;

    public NewsItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        mNews = mRepository.getAllNewsItems();
    }

    public LiveData<List<NewsItem>> getNews() { return mNews; }

    public void syncNews() {mRepository.resyncDBwithAPI();}
}
