package com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.service

import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model.CharacterListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("api/character")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterListResponseDto>
}