package ke.newsarticles.feature_tourist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ke.newsarticles.feature_tourist.data.api.TouristApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {
    @Provides
    @Singleton
    fun provideTouristApi(retrofit: Retrofit): TouristApi = retrofit.create(TouristApi::class.java)
}