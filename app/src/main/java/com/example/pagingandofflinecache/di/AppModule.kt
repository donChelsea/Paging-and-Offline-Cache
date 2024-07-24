package com.example.pagingandofflinecache.di

import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.pagingandofflinecache.data.local.CharacterDao
import com.example.pagingandofflinecache.data.local.CharacterDatabase
import com.example.pagingandofflinecache.data.local.CharacterEntity
import com.example.pagingandofflinecache.data.remote.CharacterRemoteMediator
import com.example.pagingandofflinecache.data.remote.RickAndMortyApi
import com.example.pagingandofflinecache.data.remote.RickAndMortyApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRickAndMortyApi(): RickAndMortyApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyApi::class.java)

    @Provides
    @Singleton
    fun provideCharacterDatabase(@ApplicationContext context: Context): CharacterDatabase =
        Room.databaseBuilder(
            context,
            CharacterDatabase::class.java,
            "character_database"
        ).build()

    @Provides
    @Singleton
    fun provideCharacterPager(database: CharacterDatabase, api: RickAndMortyApi): Pager<Int, CharacterEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CharacterRemoteMediator(
                database = database,
                api = api
            ),
            pagingSourceFactory = {
                database.dao.pagingSource()
            }
        )
    }
}