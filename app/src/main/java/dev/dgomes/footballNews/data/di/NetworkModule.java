package dev.dgomes.footballNews.data.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dev.dgomes.footballNews.data.remote.NewsRemoteDataSource;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    private static final String BASE_URL = "https://dgomesdev.github.io/Api-simulations/";

    @Provides
    @Singleton // Retrofit instance should usually be a singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton // Your remote API service should usually be a singleton
    public NewsRemoteDataSource provideNewsRemoteDataSource(Retrofit retrofit) {
        return retrofit.create(NewsRemoteDataSource.class);
    }
}
