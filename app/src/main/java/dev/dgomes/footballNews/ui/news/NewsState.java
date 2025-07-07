package dev.dgomes.footballNews.ui.news;

import java.util.Collections;
import java.util.List;

import dev.dgomes.footballNews.domain.NewsModel;

public class NewsState {
    private final NewsStatus status;
    private final List<NewsModel> newsList;
    private final String errorMessage;

    public NewsState() {
        this(NewsStatus.LOADING, Collections.emptyList(), "");
    }

    private NewsState(NewsStatus status, List<NewsModel> newsList, String errorMessage) {
        this.status = status;
        this.newsList = List.copyOf(newsList);
        this.errorMessage = errorMessage;
    }

    // Overloads for convenience
    public NewsState setLoading() {
        return new NewsState(
                NewsStatus.LOADING,
                this.newsList,
                this.errorMessage
        );
    }

    public NewsState setSuccess(List<NewsModel> newNewsList) {
        return new NewsState(
                NewsStatus.SUCCESS,
                newNewsList,
                ""
        );
    }

    public NewsState setError(String newErrorMessage) {
        return new NewsState(
                NewsStatus.ERROR,
                this.newsList,
                newErrorMessage
        );
    }

    public NewsStatus getStatus() {
        return this.status;
    }

    public List<NewsModel> getAllNewsList() {
        return List.copyOf(this.newsList);
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
