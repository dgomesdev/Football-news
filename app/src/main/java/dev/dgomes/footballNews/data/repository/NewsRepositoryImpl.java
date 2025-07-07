package dev.dgomes.footballNews.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import dev.dgomes.footballNews.data.local.NewsDao;
import dev.dgomes.footballNews.data.remote.NewsRemoteDataSource;
import dev.dgomes.footballNews.data.remote.NewsRemoteEntity;
import dev.dgomes.footballNews.domain.ApiException;
import dev.dgomes.footballNews.domain.NewsModel;
import dev.dgomes.footballNews.domain.NewsRepository;
import dev.dgomes.footballNews.domain.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class NewsRepositoryImpl implements NewsRepository {

    final NewsRemoteDataSource remoteApi;
    final NewsDao newsDao;
    final ExecutorService dbExecutor;

    @Inject
    public NewsRepositoryImpl(
            NewsRemoteDataSource remoteApi,
            NewsDao newsDao,
            ExecutorService dbExecutor
    ) {
        this.remoteApi = remoteApi;
        this.newsDao = newsDao;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public LiveData<Result<List<NewsModel>>> getAllNews() {
        MutableLiveData<Result<List<NewsModel>>> result = new MutableLiveData<>();
        try {

            return Transformations.map(newsDao.getAllNews(), list -> {
                try {
                    List<NewsModel> mapped = list.stream()
                            .map(NewsModel::fromLocal)
                            .collect(Collectors.toList());
                    return new Result.Success<>(mapped);
                } catch (Exception e) {
                    return new Result.Error<>(e);
                }
            });
        } catch (Exception e) {
            result.setValue(new Result.Error<>(e));
            return result;
        }
    }

    @Override
    public LiveData<Result<List<NewsModel>>> getFavoriteNews() {
        MutableLiveData<Result<List<NewsModel>>> result = new MutableLiveData<>();
        try {

            return Transformations.map(newsDao.getFavoriteNews(), list -> {
                try {
                    List<NewsModel> mapped = list.stream()
                            .map(NewsModel::fromLocal)
                            .collect(Collectors.toList());
                    return new Result.Success<>(mapped);
                } catch (Exception e) {
                    return new Result.Error<>(e);
                }
            });
        } catch (Exception e) {
            result.setValue(new Result.Error<>(e));
            return result;
        }
    }

    @Override
    public void updateFavoriteStatus(int newsId, boolean isFavorite) {
        dbExecutor.execute(() -> newsDao.updateFavoriteStatus(newsId, isFavorite));
    }

    @Override
    public void fetchNews() {
        remoteApi.getNews().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<NewsRemoteEntity>> call, @NonNull Response<List<NewsRemoteEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dbExecutor.execute(() ->
                            response.body().forEach(remote ->
                                    newsDao.save(NewsModel.fromRemote(remote).toLocalNews()
                                    )
                            )
                    );
                } else {
                    throw new ApiException(response.message(), response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NewsRemoteEntity>> call, @NonNull Throwable t) {
                throw new ApiException(t.getMessage(), 500);
            }
        });
    }
}