package dev.dgomes.footballNews.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "newsdata")
public record NewsLocalEntity(
        @PrimaryKey
        int id,
        String title,
        String text,
        String image,
        String url,
        boolean isFavorite
) {
}