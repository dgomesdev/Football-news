package dev.dgomes.footballNews.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsRemoteDataSource {

    @GET("footballnews.json")
    Call<List<NewsRemoteEntity>> getNews();

}
