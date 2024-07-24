package com.example.pagingandofflinecache.data.mappers

import com.example.pagingandofflinecache.data.local.CharacterEntity
import com.example.pagingandofflinecache.data.remote.CharacterDto
import com.example.pagingandofflinecache.domain.models.Character

fun CharacterDto.toCharacterEntity(): CharacterEntity = CharacterEntity(
    id = id,
    name = name,
    status = status,
    image = image,
)

fun CharacterEntity.toCharacter(): Character = Character(
    id = id,
    name = name,
    status = status,
    image = image,
)