package dev.dgomes.footballNews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import dev.dgomes.footballNews.data.FootballNewsService;
import dev.dgomes.footballNews.domain.NewsData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<NewsData>> newsList = new MutableLiveData<>();
    private final FootballNewsService api;

    public NewsViewModel() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dgomesdev.github.io/Api-simulations/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(FootballNewsService.class);
        this.findNews();
    }

    private void findNews() {
        api.getNews().enqueue(new Callback<List<NewsData>>() {
            @Override
            public void onResponse(Call<List<NewsData>> call, Response<List<NewsData>> response) {
                if (response.isSuccessful()) newsList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<NewsData>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<NewsData>> getNews() {
        return newsList;
    }
}