package ke.newsarticles.feature_tourist.domain.repositories

import androidx.paging.PagingData
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import ke.newsarticles.core_database.data.entities.TouristEntity
import kotlinx.coroutines.flow.Flow
import ke.newsarticles.core_network.data.ResponseState
import ke.newsarticles.feature_tourist.domain.models.TouristModel


interface TouristRepository {
    suspend fun getTourists(): ResponseState<String>

    suspend fun listenForTourists(): Flow<List<TouristModel>>

    fun getTouristArticlesPaged(): Flow<PagingData<TouristEntity>>
}