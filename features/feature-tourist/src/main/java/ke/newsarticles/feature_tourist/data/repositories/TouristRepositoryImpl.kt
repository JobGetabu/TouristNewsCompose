package ke.newsarticles.feature_tourist.data.repositories

import ke.newsarticles.core_database.data.dao.TouristDao
import ke.newsarticles.core_network.data.ResponseState
import ke.newsarticles.feature_tourist.data.api.TouristApi
import ke.newsarticles.feature_tourist.data.mappers.toTouristEntity
import ke.newsarticles.feature_tourist.data.mappers.toTouristModel
import ke.newsarticles.feature_tourist.domain.models.TouristModel
import ke.newsarticles.feature_tourist.domain.repositories.TouristRepository
import ke.newsarticles.utils.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TouristRepositoryImpl @Inject constructor(
    val touristApi: TouristApi, val touristDao: TouristDao, val appDispatchers: AppDispatchers
) : TouristRepository {
    override suspend fun getTourists(): ResponseState<String> {
        return try {
            val tourists = touristApi.fetchTourists().data

            if (!tourists.isNullOrEmpty()) {
                withContext(appDispatchers.io()) {
                    touristDao.deleteAllTouristEntities()
                    val touristEntities = tourists.map { it?.toTouristEntity() }.toList()
                    touristEntities.let { its -> touristDao.insertAll(its) }
                }
            }
            ResponseState.Success("Tourists fetched")

        } catch (e: Exception) {
            ResponseState.Error("Unable to fetch Tourists at the moment!Kindly try again later")
        }
    }

    override suspend fun listenForTourists(): Flow<List<TouristModel>> = flow {
        val tourists = touristDao.fetchAllTouristEntities()
        tourists.collect { touristsEntities ->
            if (touristsEntities.isNotEmpty()) {
                val touristModels = touristsEntities.map { o -> o.toTouristModel() }
                emit(touristModels)
            }
        }
    }.flowOn(appDispatchers.io())
}