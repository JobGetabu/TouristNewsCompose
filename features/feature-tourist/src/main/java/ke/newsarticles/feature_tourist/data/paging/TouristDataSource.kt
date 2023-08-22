package ke.newsarticles.feature_tourist.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ke.newsarticles.core_database.data.database.AppDatabase
import ke.newsarticles.core_database.data.entities.RemoteKeys
import ke.newsarticles.core_database.data.entities.TouristEntity
import ke.newsarticles.feature_tourist.data.api.TouristApi
import ke.newsarticles.feature_tourist.data.mappers.toTouristEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TouristRemoteMediator @Inject constructor(
    private val touristApi: TouristApi,
    private val appDatabase: AppDatabase,
) : RemoteMediator<Int, TouristEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TouristEntity>
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
            val apiResponse = touristApi.fetchTourists(page = page)

            val touristResponse = apiResponse.data
            val endOfPaginationReached = apiResponse.page == apiResponse.totalPages

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.provideRemoteKeysDaoDao().clearRemoteKeys()
                    appDatabase.provideTouristDao().deleteAllTouristEntities()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1

                val remoteKeys = touristResponse?.map {
                    RemoteKeys(
                        id = it?.id ?: 0,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                remoteKeys?.let { all -> appDatabase.provideRemoteKeysDaoDao().insertAll(all) }

                val entities = touristResponse?.map { k -> k?.toTouristEntity() }
                entities?.let { all -> appDatabase.provideTouristDao().insertAll(all) }

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TouristEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                appDatabase.provideRemoteKeysDaoDao().getRemoteKeyByID(id)
            }
        }
    }


    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TouristEntity>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            appDatabase.provideRemoteKeysDaoDao().getRemoteKeyByID(it.id ?: 0)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TouristEntity>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {
            appDatabase.provideRemoteKeysDaoDao().getRemoteKeyByID(it.id ?: 0)
        }
    }
}

const val PAGE_SIZE = 20

