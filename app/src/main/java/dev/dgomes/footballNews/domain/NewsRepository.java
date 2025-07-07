package dev.dgomes.footballNews.domain;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface NewsRepository {
    LiveData<Result<List<NewsModel>>> getAllNews();
    LiveData<Result<List<NewsModel>>> getFavoriteNews();
    void fetchNews();
    void updateFavoriteStatus(int newsId, boolean isFavorite);
}
