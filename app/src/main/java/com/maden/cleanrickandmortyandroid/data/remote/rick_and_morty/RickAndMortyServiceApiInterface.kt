package com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty

import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model.CharacterListResponseDto

interface RickAndMortyServiceApiInterface {
    suspend fun getCharacters(page: Int): DataResource<CharacterListResponseDto>
}