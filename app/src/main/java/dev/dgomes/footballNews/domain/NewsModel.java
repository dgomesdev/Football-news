package dev.dgomes.footballNews.domain;

import androidx.annotation.NonNull;

import dev.dgomes.footballNews.data.local.NewsLocalEntity;
import dev.dgomes.footballNews.data.remote.NewsRemoteEntity;

public class NewsModel {

    public final int id;
    public final String title;
    public final String text;
    public final String image;
    public final String url;
    public final boolean isFavorite;

    private NewsModel(
            int id,
            String title,
            String text,
            String image,
            String url,
            boolean isFavorite
    ) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
        this.url = url;
        this.isFavorite = isFavorite;
    }

    public static NewsModel fromRemote(NewsRemoteEntity remote) {
        return new NewsModel(
                remote.id(),
                remote.title(),
                remote.text(),
                remote.image(),
                remote.url(),
                false
        );
    }

    public static NewsModel fromLocal(NewsLocalEntity local) {
        return new NewsModel(
                local.id(),
                local.title(),
                local.text(),
                local.image(),
                local.url(),
                local.isFavorite()
        );
    }

    public NewsLocalEntity toLocalNews() {
        return new NewsLocalEntity(
                id,
                title,
                text,
                image,
                url,
                false
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "NewsModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
