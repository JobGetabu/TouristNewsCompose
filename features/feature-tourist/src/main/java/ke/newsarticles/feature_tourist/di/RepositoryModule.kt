package ke.newsarticles.feature_tourist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ke.newsarticles.core_database.data.dao.TouristDao
import ke.newsarticles.feature_tourist.data.api.TouristApi
import ke.newsarticles.feature_tourist.data.repositories.TouristRepositoryImpl
import ke.newsarticles.feature_tourist.domain.repositories.TouristRepository
import ke.newsarticles.utils.AppDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTouristRepository(touristApi: TouristApi, touristDao: TouristDao, appDispatchers: AppDispatchers): TouristRepository =
        TouristRepositoryImpl(touristApi, touristDao, appDispatchers)

}