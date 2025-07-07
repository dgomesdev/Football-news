package dev.dgomes.footballNews.data.di;

import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import dev.dgomes.footballNews.data.local.NewsDao;
import dev.dgomes.footballNews.data.local.NewsLocalDataSource;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    private static final String DB_NAME = "news-database";

    @Provides
    @Singleton // Room database instance should be a singleton
    public NewsLocalDataSource provideNewsLocalDataSource(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                    context,
                    NewsLocalDataSource.class,
                    DB_NAME
               ).build();
    }

    @Provides
    @Singleton
    public NewsDao provideNewsDao(NewsLocalDataSource database) {
        return database.newsDao();
    }

    @Provides
    @Singleton
    public ExecutorService provideDbExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
