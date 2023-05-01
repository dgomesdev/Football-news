package dev.dgomes.footballNews.ui.news;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dev.dgomes.footballNews.data.FootballNewsRepository;
import dev.dgomes.footballNews.domain.NewsData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    public enum State {
        LOADING, SUCCESS, ERROR;
    }

    private final MutableLiveData<List<NewsData>> newsList = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();

    public NewsViewModel() {
        loadNews();
    }

    public void loadNews() {
        state.setValue(State.LOADING);
        FootballNewsRepository.getInstance().getRemoteApi().getNews().enqueue(new Callback<List<NewsData>>() {
            @Override
            public void onResponse(Call<List<NewsData>> call, Response<List<NewsData>> response) {
                if (response.isSuccessful()) {
                    newsList.setValue(response.body());
                    state.setValue(State.SUCCESS);
                } else state.setValue(State.ERROR);
            }

            @Override
            public void onFailure(Call<List<NewsData>> call, Throwable error) {
                state.setValue(State.ERROR);
            }
        });
    }

    public void saveNews(NewsData news) {
        AsyncTask.execute(() -> FootballNewsRepository.getInstance().getDatabase().newsDao().save(news));
        }

    public LiveData<List<NewsData>> getNews() {
        return this.newsList;
    }

    public LiveData<State> getState() {
        return this.state;
    }
}