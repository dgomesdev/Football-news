package dev.dgomes.footballNews.data;

import androidx.room.Room;

import dev.dgomes.footballNews.App;
import dev.dgomes.footballNews.data.local.NewsDatabase;
import dev.dgomes.footballNews.data.remote.FootballNewsService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FootballNewsRepository {

    private static final String REMOTE_API_URL = "https://dgomesdev.github.io/Api-simulations/";
    private static final String DB_NAME = "football-news-database";

    private final FootballNewsService remoteApi;
    private final NewsDatabase database;

    public FootballNewsService getRemoteApi() {
        return remoteApi;
    }

    public NewsDatabase getDatabase() {
        return database;
    }

    private FootballNewsRepository() {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FootballNewsService.class);

        database = Room.databaseBuilder(App.getInstance(), NewsDatabase.class, DB_NAME)
                .build();
    }

    public static FootballNewsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FootballNewsRepository INSTANCE = new FootballNewsRepository();
    }
}
