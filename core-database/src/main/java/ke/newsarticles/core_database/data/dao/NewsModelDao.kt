package ke.newsarticles.core_database.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsModelDao {
    @Insert
    suspend fun insertAll(newsModelEntitys: List<NewsModelEntity?>)

    @Insert
    suspend fun insert(newsModelEntity: NewsModelEntity)

    @Query("SELECT * FROM news")
    fun fetchAllNewsModels(): Flow<List<NewsModelEntity>>

    @Query("SELECT * FROM news WHERE id=:id")
    fun fetchNewsModelById(id: Int): Flow<NewsModelEntity>

    @Query("DELETE FROM news")
    suspend fun deleteAllNewsModels()
}