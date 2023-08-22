package ke.newsarticles.core_database.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ke.newsarticles.core_database.data.converters.MultiMediaListTypeConverter
import ke.newsarticles.core_database.data.dao.NewsModelDao
import ke.newsarticles.core_database.data.dao.RemoteKeysDao
import ke.newsarticles.core_database.data.dao.TouristDao
import ke.newsarticles.core_database.data.entities.MultiMediaEntity
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import ke.newsarticles.core_database.data.entities.RemoteKeys
import ke.newsarticles.core_database.data.entities.TouristEntity
import ke.newsarticles.core_database.data.entities.UserEntity

@Database(
    exportSchema = false,
    version = 1,
    entities = [NewsModelEntity::class, MultiMediaEntity::class, UserEntity::class, TouristEntity::class, RemoteKeys::class]
)
@TypeConverters(MultiMediaListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun provideTouristDao(): TouristDao
    abstract fun provideNewsModelDao(): NewsModelDao
    abstract fun provideRemoteKeysDaoDao(): RemoteKeysDao
}