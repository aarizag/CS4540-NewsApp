package com.example.adryel.newsapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NewsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsItem newsItem);

    @Query("DELETE FROM news_item")
    void clearAll();

    @Query("SELECT * FROM news_item ORDER BY id ASC")
    LiveData<List<NewsItem>> loadAllNewsItems();
}

