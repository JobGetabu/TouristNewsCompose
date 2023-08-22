package ke.newsarticles.feature_tourist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ke.newsarticles.core_database.data.database.AppDatabase
import ke.newsarticles.feature_tourist.data.api.TouristApi
import ke.newsarticles.feature_tourist.data.paging.TouristRemoteMediator
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {
    @Provides
    @Singleton
    fun provideTouristApi(retrofit: Retrofit): TouristApi = retrofit.create(TouristApi::class.java)

    @Provides
    @Singleton
    fun provideTouristRemoteMediator(touristApi: TouristApi, appDatabase: AppDatabase): TouristRemoteMediator = TouristRemoteMediator(touristApi, appDatabase)
}