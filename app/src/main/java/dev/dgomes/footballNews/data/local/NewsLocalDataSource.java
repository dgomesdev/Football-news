package dev.dgomes.footballNews.data.local;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface NewsLocalDataSource {
    LiveData<List<NewsLocalEntity>> getAllNews();
    LiveData<List<NewsLocalEntity>> getFavoriteNews();
    void save(NewsLocalEntity news);
    void updateFavoriteStatus(long newsId, boolean isFavorite);
}
