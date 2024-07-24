package com.example.pagingandofflinecache.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): ApiResult<CharacterDto>

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}
