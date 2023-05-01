package dev.dgomes.footballNews.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import dev.dgomes.footballNews.domain.NewsData;

public interface FootballNewsService {

    @GET("footballnews.json")
    Call<List<NewsData>> getNews();

}
