package ke.newsarticles.core_database.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsModelEntitys: List<NewsModelEntity?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsModelEntity: NewsModelEntity)

    @Query("SELECT * FROM news")
    fun fetchAllNewsModels():  Flow<List<NewsModelEntity>> //PagingSource<Int, NewsModelEntity>

    @Query("SELECT * FROM news")
    fun fetchAllNewsModelsPaged(): PagingSource<Int, NewsModelEntity>

    @Query("SELECT * FROM news WHERE id=:id")
    fun fetchNewsModelById(id: Int): Flow<NewsModelEntity>

    @Query("DELETE FROM news")
    suspend fun deleteAllNewsModels()
}