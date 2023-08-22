package ke.newsarticles.feature_news.domain.repositories

import androidx.paging.PagingData
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import ke.newsarticles.core_network.data.ResponseState
import ke.newsarticles.feature_news.domain.models.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsArticles(): ResponseState<String>

    suspend fun listenForNewsArticles(): Flow<List<NewsModel>>

    fun getNewsArticlesPaged(): Flow<PagingData<NewsModelEntity>>
}