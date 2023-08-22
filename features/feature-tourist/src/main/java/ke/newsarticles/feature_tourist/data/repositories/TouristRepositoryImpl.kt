package ke.newsarticles.feature_tourist.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ke.newsarticles.core_database.data.dao.TouristDao
import ke.newsarticles.core_database.data.database.AppDatabase
import ke.newsarticles.core_database.data.entities.TouristEntity
import ke.newsarticles.core_network.data.ResponseState
import ke.newsarticles.feature_tourist.data.api.TouristApi
import ke.newsarticles.feature_tourist.data.mappers.toTouristEntity
import ke.newsarticles.feature_tourist.data.mappers.toTouristModel
import ke.newsarticles.feature_tourist.data.paging.PAGE_SIZE
import ke.newsarticles.feature_tourist.data.paging.TouristRemoteMediator
import ke.newsarticles.feature_tourist.domain.models.TouristModel
import ke.newsarticles.feature_tourist.domain.repositories.TouristRepository
import ke.newsarticles.utils.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TouristRepositoryImpl @Inject constructor(
    val touristApi: TouristApi, val appDatabase: AppDatabase, val appDispatchers: AppDispatchers, val touristRemoteMediator: TouristRemoteMediator
) : TouristRepository {
    override suspend fun getTourists(): ResponseState<String> {
        return try {
            val tourists = touristApi.fetchTourists().data

            if (!tourists.isNullOrEmpty()) {
                withContext(appDispatchers.io()) {
                    appDatabase.provideTouristDao().deleteAllTouristEntities()
                    val touristEntities = tourists.map { it?.toTouristEntity() }.toList()
                    touristEntities.let { its -> appDatabase.provideTouristDao().insertAll(its) }
                }
            }
            ResponseState.Success("Tourists fetched")

        } catch (e: Exception) {
            ResponseState.Error("Unable to fetch Tourists at the moment!Kindly try again later")
        }
    }

    override suspend fun listenForTourists(): Flow<List<TouristModel>> = flow {
        val tourists = appDatabase.provideTouristDao().fetchAllTouristEntities()
        tourists.collect { touristsEntities ->
            if (touristsEntities.isNotEmpty()) {
                val touristModels = touristsEntities.map { o -> o.toTouristModel() }
                emit(touristModels)
            }
        }
    }.flowOn(appDispatchers.io())

    @OptIn(ExperimentalPagingApi::class)
    override fun getTouristArticlesPaged(): Flow<PagingData<TouristEntity>>  =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                appDatabase.provideTouristDao().fetchAllTouristEntitiesPaged()
            },
            remoteMediator = touristRemoteMediator
        ).flow
}