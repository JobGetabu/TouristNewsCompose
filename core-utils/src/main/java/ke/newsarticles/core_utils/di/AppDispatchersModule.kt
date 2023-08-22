package ke.newsarticles.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ke.newsarticles.utils.AppDispatchers
import ke.newsarticles.utils.AppDispatchersImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppDispatchersModule {

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers = AppDispatchersImpl()
}