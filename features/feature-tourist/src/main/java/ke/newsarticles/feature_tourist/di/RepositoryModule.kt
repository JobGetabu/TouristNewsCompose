package ke.newsarticles.feature_tourist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ke.newsarticles.core_database.data.dao.TouristDao
import ke.newsarticles.core_database.data.database.AppDatabase
import ke.newsarticles.feature_tourist.data.api.TouristApi
import ke.newsarticles.feature_tourist.data.paging.TouristRemoteMediator
import ke.newsarticles.feature_tourist.data.repositories.TouristRepositoryImpl
import ke.newsarticles.feature_tourist.domain.repositories.TouristRepository
import ke.newsarticles.utils.AppDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTouristRepository(touristApi: TouristApi, appDatabase: AppDatabase, appDispatchers: AppDispatchers, remoteMediator: TouristRemoteMediator): TouristRepository =
        TouristRepositoryImpl(touristApi, appDatabase, appDispatchers, remoteMediator)

}