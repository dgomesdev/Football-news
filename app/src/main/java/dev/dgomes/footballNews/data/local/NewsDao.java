package dev.dgomes.footballNews.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import dev.dgomes.footballNews.domain.NewsData;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(NewsData news);

    @Query("SELECT * FROM newsdata WHERE isFavorite = 1")
    LiveData<List<NewsData>> loadFavoriteNews();

}
