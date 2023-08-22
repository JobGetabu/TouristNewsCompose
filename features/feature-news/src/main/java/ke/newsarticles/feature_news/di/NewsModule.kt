package ke.newsarticles.feature_news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ke.newsarticles.core_database.data.database.AppDatabase
import ke.newsarticles.feature_news.data.api.NewsApi
import ke.newsarticles.feature_news.data.paging.NewsRemoteMediator
import ke.newsarticles.feature_news.domain.models.NewsModel
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {
    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsRemoteMediator(newsApi: NewsApi, appDatabase: AppDatabase): NewsRemoteMediator = NewsRemoteMediator(newsApi, appDatabase)
}