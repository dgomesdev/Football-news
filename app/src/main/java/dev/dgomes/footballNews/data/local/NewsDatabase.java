package dev.dgomes.footballNews.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import dev.dgomes.footballNews.domain.NewsData;

@Database(entities = {NewsData.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
