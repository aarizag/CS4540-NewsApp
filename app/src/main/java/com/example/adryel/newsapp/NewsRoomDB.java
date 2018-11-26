package com.example.adryel.newsapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsRoomDB extends RoomDatabase {

    public abstract NewsItemDao newsItemDao();

    private static volatile NewsRoomDB INSTANCE;

    static NewsRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsRoomDB.class, "news_item_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
