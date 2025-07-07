package dev.dgomes.footballNews.ui.favorites;

import java.util.Collections;
import java.util.List;

import dev.dgomes.footballNews.domain.NewsModel;

public class FavoritesState {
    private final FavoritesStatus status;
    private final List<NewsModel> favoritesList;
    private final String errorMessage;

    public FavoritesState() {
        this(FavoritesStatus.LOADING, Collections.emptyList(), "");
    }

    private FavoritesState(FavoritesStatus status, List<NewsModel> newsList, String errorMessage) {
        this.status = status;
        this.favoritesList = List.copyOf(newsList);
        this.errorMessage = errorMessage;
    }

    // Overloads for convenience
    public FavoritesState setLoading() {
        return new FavoritesState(
                FavoritesStatus.LOADING,
                this.favoritesList,
                this.errorMessage
        );
    }

    public FavoritesState setSuccess(List<NewsModel> newNewsList) {
        return new FavoritesState(
                FavoritesStatus.SUCCESS,
                newNewsList,
                ""
        );
    }

    public FavoritesState setError(String newErrorMessage) {
        return new FavoritesState(
                FavoritesStatus.ERROR,
                this.favoritesList,
                newErrorMessage
        );
    }

    public FavoritesStatus getStatus() {
        return this.status;
    }

    public List<NewsModel> getFavoritesList() {
        return List.copyOf(this.favoritesList);
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
