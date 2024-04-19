package com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model

data class CharacterListResponseDto(
    val info: InfoDto,
    val results: List<ResultDto>
)