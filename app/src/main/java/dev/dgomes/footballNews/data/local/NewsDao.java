package dev.dgomes.footballNews.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM newsdata")
    LiveData<List<NewsLocalEntity>> getAllNews();

    @Query("SELECT * FROM newsdata WHERE isFavorite = 1")
    LiveData<List<NewsLocalEntity>> getFavoriteNews();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void save(NewsLocalEntity news);

    @Query("UPDATE newsdata SET isFavorite = :isFavorite WHERE id = :newsId")
    void updateFavoriteStatus(long newsId, boolean isFavorite);
}
