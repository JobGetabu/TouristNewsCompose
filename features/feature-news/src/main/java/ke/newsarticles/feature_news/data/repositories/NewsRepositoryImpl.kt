package ke.newsarticles.feature_news.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ke.newsarticles.core_database.data.dao.NewsModelDao
import ke.newsarticles.core_database.data.database.AppDatabase
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import ke.newsarticles.core_network.data.ResponseState
import ke.newsarticles.feature_news.data.api.NewsApi
import ke.newsarticles.feature_news.data.mappers.toNewsModel
import ke.newsarticles.feature_news.data.mappers.toNewsModelEntity
import ke.newsarticles.feature_news.data.paging.NewsRemoteMediator
import ke.newsarticles.feature_news.data.paging.PAGE_SIZE
import ke.newsarticles.feature_news.domain.models.NewsModel
import ke.newsarticles.feature_news.domain.repositories.NewsRepository
import ke.newsarticles.utils.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val newsApi: NewsApi, val appDatabase: AppDatabase, val appDispatchers: AppDispatchers, val newsRemoteMediator: NewsRemoteMediator
) : NewsRepository {
    override suspend fun getNewsArticles(): ResponseState<String> {
         return try {
            val news = newsApi.fetchNews().data

            if (!news.isNullOrEmpty()) {
                withContext(appDispatchers.io()) {
                    appDatabase.provideNewsModelDao().deleteAllNewsModels()
                    val newsEntities = news.map { it?.toNewsModelEntity(it.id ?: 0)}.toList()
                    newsEntities.let { its ->  appDatabase.provideNewsModelDao().insertAll(its) }
                }
            }
            ResponseState.Success("News fetched")
        } catch (e: Exception) {
            ResponseState.Error("Unable to fetch News at the moment!Kindly try again later")
        }
    }

    override suspend fun listenForNewsArticles(): Flow<List<NewsModel>> = flow {
            val news = appDatabase.provideNewsModelDao().fetchAllNewsModels()
            news.collect{ newsEntities ->
                if(newsEntities.isNotEmpty()){
                    val newModels = newsEntities.map { k -> k.toNewsModel() }
                    emit(newModels)
                }
            }
    }.flowOn(appDispatchers.io())

    @OptIn(ExperimentalPagingApi::class)
    override fun getNewsArticlesPaged(): Flow<PagingData<NewsModelEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                appDatabase.provideNewsModelDao().fetchAllNewsModelsPaged()
            },
            remoteMediator = newsRemoteMediator
        ).flow

}