package ke.newsarticles.feature_news.data.repositories

import ke.newsarticles.core_database.data.dao.NewsModelDao
import ke.newsarticles.core_network.data.ResponseState
import ke.newsarticles.feature_news.data.api.NewsApi
import ke.newsarticles.feature_news.data.mappers.toNewsModel
import ke.newsarticles.feature_news.data.mappers.toNewsModelEntity
import ke.newsarticles.feature_news.domain.models.NewsModel
import ke.newsarticles.feature_news.domain.repositories.NewsRepository
import ke.newsarticles.utils.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val newsApi: NewsApi, val newsModelDao: NewsModelDao, val appDispatchers: AppDispatchers
) : NewsRepository {
    override suspend fun getNewsArticles(): ResponseState<String> {
         return try {
            val news = newsApi.fetchNews().data

            if (!news.isNullOrEmpty()) {
                withContext(appDispatchers.io()) {
                    newsModelDao.deleteAllNewsModels()
                    val newsEntities = news.map { it?.toNewsModelEntity(it.id ?: 0)}.toList()
                    newsEntities.let { its ->  newsModelDao.insertAll(its) }
                }
            }
            ResponseState.Success("News fetched")
        } catch (e: Exception) {
            ResponseState.Error("Unable to fetch News at the moment!Kindly try again later")
        }
    }

    override suspend fun listenForNewsArticles(): Flow<List<NewsModel>> = flow {
            val news = newsModelDao.fetchAllNewsModels()
            news.collect{ newsEntities ->
                if(newsEntities.isNotEmpty()){
                    val newModels = newsEntities.map { k -> k.toNewsModel() }
                    emit(newModels)
                }
            }
    }.flowOn(appDispatchers.io())

}