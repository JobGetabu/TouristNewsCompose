package ke.newsarticles.core_database.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import ke.newsarticles.core_database.data.entities.TouristEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TouristDao {
    @Insert
    suspend fun insertAll(touristEntity: List<TouristEntity?>)

    @Insert
    suspend fun insert(touristEntity: TouristEntity)

    @Query("SELECT * FROM tourists")
    fun fetchAllTouristEntities(): Flow<List<TouristEntity>>

    @Query("SELECT * FROM tourists WHERE id=:id")
    fun fetchTouristEntityById(id: Int): Flow<TouristEntity>

    @Query("DELETE FROM news")
    suspend fun deleteAllTouristEntities()
}