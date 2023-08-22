package ke.newsarticles.core_database.di

import android.content.Context
import androidx.room.Room
import ke.newsarticles.core_database.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ke.newsarticles.core_database.data.dao.NewsModelDao
import ke.newsarticles.core_database.data.dao.RemoteKeysDao
import ke.newsarticles.core_database.data.dao.TouristDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "NewsArticleDb").build()

    @Singleton
    @Provides
    fun provideTouristDao(appDatabase: AppDatabase): TouristDao = appDatabase.provideTouristDao()

    @Singleton
    @Provides
    fun provideNewsModelDao(appDatabase: AppDatabase): NewsModelDao = appDatabase.provideNewsModelDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(appDatabase: AppDatabase): RemoteKeysDao = appDatabase.provideRemoteKeysDaoDao()
}