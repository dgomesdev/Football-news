package dev.dgomes.footballNews.data.di;

import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import dev.dgomes.footballNews.data.local.NewsDao;
import dev.dgomes.footballNews.data.local.NewsDatabase;
import dev.dgomes.footballNews.data.local.NewsLocalDataSource;
import dev.dgomes.footballNews.data.local.NewsLocalDataSourceImpl;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    private static final String DB_NAME = "news-database";

    @Provides
    @Singleton
    public NewsDatabase provideNewsDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                NewsDatabase.class,
                DB_NAME
        ).build();
    }

    @Provides
    @Singleton
    public NewsDao provideNewsDao(NewsDatabase database) {
        return database.newsDao();
    }

    @Provides
    @Singleton
    public ExecutorService provideDbExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    public NewsLocalDataSource provideNewsLocalDataSource(NewsDao dao) {
        return new NewsLocalDataSourceImpl(dao);
    }
}
