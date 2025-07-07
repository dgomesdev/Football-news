package dev.dgomes.footballNews.data.local;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

public class NewsLocalDataSourceImpl implements NewsLocalDataSource {

    private final NewsDao newsDao;

    @Inject
    public NewsLocalDataSourceImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public LiveData<List<NewsLocalEntity>> getAllNews() {
        return newsDao.getAllNews();
    }

    @Override
    public LiveData<List<NewsLocalEntity>> getFavoriteNews() {
        return newsDao.getFavoriteNews();
    }

    @Override
    public void save(NewsLocalEntity news) {
        newsDao.save(news);
    }

    @Override
    public void updateFavoriteStatus(long newsId, boolean isFavorite) {
        newsDao.updateFavoriteStatus(newsId, isFavorite);
    }
}
