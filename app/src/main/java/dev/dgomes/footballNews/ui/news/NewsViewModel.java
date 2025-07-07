package dev.dgomes.footballNews.ui.news;

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
public class NewsViewModel extends ViewModel {

    private final NewsRepository newsRepository;

    private final MediatorLiveData<NewsState> _uiState = new MediatorLiveData<>();
    public LiveData<NewsState> getNewsState() {
        return _uiState;
    }

    @Inject
    public NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        _uiState.setValue(new NewsState().setLoading());

        _uiState.addSource(newsRepository.getAllNews(), result -> {
            NewsState current = _uiState.getValue();
            if (current == null) return;

            if (result.isSuccess()) {
                List<NewsModel> data = ((Result.Success<List<NewsModel>>) result).data;
                _uiState.setValue(current.setSuccess(data));
            } else if (result instanceof Result.Error) {
                Exception error = ((Result.Error<?>) result).exception;
                _uiState.setValue(current.setError(error.getMessage()));
            }
        });
    }

    public void fetchNews() {
        assert _uiState.getValue() != null;
        _uiState.setValue(_uiState.getValue().setLoading());
        newsRepository.fetchNews();
    }

    public void toggleFavorite(NewsModel news) {
        NewsState current = _uiState.getValue();
        if (current != null) {
            _uiState.setValue(current.setLoading());
        }
        newsRepository.updateFavoriteStatus(news.id, !news.isFavorite);
    }
}