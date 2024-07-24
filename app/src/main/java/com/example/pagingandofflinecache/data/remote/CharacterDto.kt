package com.example.pagingandofflinecache.data.remote

data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
)

data class ApiResult<T>(
    val results: List<T>,
    val info: InfoDto,
)

data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String,
)
