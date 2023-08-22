package ke.newsarticles.feature_news.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ke.newsarticles.core_database.data.database.AppDatabase
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import ke.newsarticles.core_database.data.entities.RemoteKeys
import ke.newsarticles.feature_news.data.api.NewsApi
import ke.newsarticles.feature_news.data.mappers.toNewsModelEntity
import ke.newsarticles.feature_news.domain.models.NewsModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val newsApi: NewsApi,
    private val appDatabase: AppDatabase,
) : RemoteMediator<Int, NewsModelEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsModelEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = newsApi.fetchNews(page = page)

            val newsResponse = apiResponse.data
            val endOfPaginationReached = apiResponse.page == apiResponse.totalPages

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.provideRemoteKeysDaoDao().clearRemoteKeys()
                    appDatabase.provideNewsModelDao().deleteAllNewsModels()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1

                val remoteKeys = newsResponse?.map {
                    RemoteKeys(
                        id = it?.id ?: 0,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                remoteKeys?.let { all -> appDatabase.provideRemoteKeysDaoDao().insertAll(all) }

                val entities = newsResponse?.map { k -> k?.toNewsModelEntity(k.id!!) }
                entities?.let { all -> appDatabase.provideNewsModelDao().insertAll(all) }

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, NewsModelEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                appDatabase.provideRemoteKeysDaoDao().getRemoteKeyByID(id)
            }
        }
    }


    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, NewsModelEntity>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            appDatabase.provideRemoteKeysDaoDao().getRemoteKeyByID(it.id ?: 0)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, NewsModelEntity>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {
            appDatabase.provideRemoteKeysDaoDao().getRemoteKeyByID(it.id ?: 0)
        }
    }
}

const val PAGE_SIZE = 20

