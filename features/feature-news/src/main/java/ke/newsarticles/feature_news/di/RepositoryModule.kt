package ke.newsarticles.feature_news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ke.newsarticles.core_database.data.dao.NewsModelDao
import ke.newsarticles.core_database.data.database.AppDatabase
import ke.newsarticles.feature_news.data.api.NewsApi
import ke.newsarticles.feature_news.data.paging.NewsRemoteMediator
import ke.newsarticles.feature_news.data.repositories.NewsRepositoryImpl
import ke.newsarticles.feature_news.domain.repositories.NewsRepository
import ke.newsarticles.utils.AppDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi, appDatabase: AppDatabase, appDispatchers: AppDispatchers, remoteMediator: NewsRemoteMediator): NewsRepository =
        NewsRepositoryImpl(newsApi, appDatabase, appDispatchers, remoteMediator)

}