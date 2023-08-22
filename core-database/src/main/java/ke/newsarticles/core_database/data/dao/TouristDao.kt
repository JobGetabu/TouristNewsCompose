package ke.newsarticles.core_database.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import ke.newsarticles.core_database.data.entities.TouristEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TouristDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(touristEntity: List<TouristEntity?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(touristEntity: TouristEntity)

    @Query("SELECT * FROM tourists")
    fun fetchAllTouristEntities(): Flow<List<TouristEntity>>

    @Query("SELECT * FROM tourists")
    fun fetchAllTouristEntitiesPaged(): PagingSource<Int, TouristEntity>

    @Query("SELECT * FROM tourists WHERE id=:id")
    fun fetchTouristEntityById(id: Int): Flow<TouristEntity>

    @Query("DELETE FROM news")
    suspend fun deleteAllTouristEntities()
}