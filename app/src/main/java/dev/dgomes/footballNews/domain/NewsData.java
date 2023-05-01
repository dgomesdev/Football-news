package dev.dgomes.footballNews.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewsData {

    @PrimaryKey
    public int id;
    public String title;
    public String text;
    public String image;
    public String url;
    public boolean isFavorite;
}
