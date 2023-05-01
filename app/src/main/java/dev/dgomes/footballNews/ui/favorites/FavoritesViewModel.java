package dev.dgomes.footballNews.ui.favorites;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dev.dgomes.footballNews.data.FootballNewsRepository;
import dev.dgomes.footballNews.domain.NewsData;

public class FavoritesViewModel extends ViewModel {

    public FavoritesViewModel() {
    }

    public LiveData<List<NewsData>> loadFavoriteNews() {
        return FootballNewsRepository.getInstance().getDatabase().newsDao().loadFavoriteNews();
    }

    public void saveNews(NewsData news) {
        AsyncTask.execute(() -> FootballNewsRepository.getInstance().getDatabase().newsDao().save(news));
    }
}
