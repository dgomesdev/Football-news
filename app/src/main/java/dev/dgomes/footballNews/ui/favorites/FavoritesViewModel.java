package dev.dgomes.footballNews.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.dgomes.footballNews.domain.NewsModel;
import dev.dgomes.footballNews.domain.NewsRepository;
import dev.dgomes.footballNews.domain.Result;

@HiltViewModel
public class FavoritesViewModel extends ViewModel {

    private final NewsRepository newsRepository;
    private final MediatorLiveData<FavoritesState> _uiState = new MediatorLiveData<>();
    public LiveData<FavoritesState> getFavoritesState() {
        return _uiState;
    }

    @Inject
    public FavoritesViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        _uiState.setValue(new FavoritesState().setLoading());

        _uiState.addSource(newsRepository.getFavoriteNews(), result -> {
            FavoritesState current = _uiState.getValue();
            if (current == null) return;

            if (result instanceof Result.Success) {
                List<NewsModel> data = ((Result.Success<List<NewsModel>>) result).data;
                _uiState.setValue(current.setSuccess(data));
            } else if (result instanceof Result.Error) {
                Exception error = ((Result.Error<?>) result).exception;
                _uiState.setValue(current.setError(error.getMessage()));
            }
        });
    }

    public void removeFavorite(NewsModel news) {
        FavoritesState current = _uiState.getValue();
        if (current != null) {
            _uiState.setValue(current.setLoading());
        }
        newsRepository.updateFavoriteStatus(news.id, false);
    }
}