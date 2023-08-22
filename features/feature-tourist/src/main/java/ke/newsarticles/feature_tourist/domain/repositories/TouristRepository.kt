package ke.newsarticles.feature_tourist.domain.repositories

import kotlinx.coroutines.flow.Flow
import ke.newsarticles.core_network.data.ResponseState
import ke.newsarticles.feature_tourist.domain.models.TouristModel


interface TouristRepository {
    suspend fun getTourists(): ResponseState<String>

    suspend fun listenForTourists(): Flow<List<TouristModel>>
}