package com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.service

import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.RickAndMortyServiceApiInterface
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model.CharacterListResponseDto
import java.lang.Exception
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val _apiService: RickAndMortyApiService
) : RickAndMortyServiceApiInterface {

    override suspend fun getCharacters(page: Int): DataResource<CharacterListResponseDto> {
        return try {
            //Request
            val response = _apiService.getCharacters(page = page)

            //Http Error handling
            if (!response.isSuccessful) {
                return DataResource.Error(
                    message = response.message().ifEmpty { "Something went wrong!" },
                )
            }

            //Response control
            if (response.body() == null) {
                return DataResource.Error(
                    message = "Something went wrong!",
                )
            }

            //Response
            DataResource.Success(data = response.body()!!)
        } catch (e: Exception) {
            //Error handling
            DataResource.Error(
                message = "Something went wrong!\n" + e.localizedMessage,
            )
        }
    }

}