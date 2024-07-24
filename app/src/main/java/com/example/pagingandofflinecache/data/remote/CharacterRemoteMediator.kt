package com.example.pagingandofflinecache.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.pagingandofflinecache.data.local.CharacterDatabase
import com.example.pagingandofflinecache.data.local.CharacterEntity
import com.example.pagingandofflinecache.data.mappers.toCharacterEntity
import kotlinx.coroutines.delay
import okio.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val database: CharacterDatabase,
    private val api: RickAndMortyApi,
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            delay(2000L)
            val characters = api.getCharacters(page = loadKey)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.dao.clearAll()
                }
                val entities = characters.results.map { it.toCharacterEntity() }
                database.dao.upsertAll(entities)
            }

            MediatorResult.Success(
                endOfPaginationReached = characters.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}