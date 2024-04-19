package com.maden.cleanrickandmortyandroid.use_case

import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.domain.model.characters.CharacterResponseModel
import com.maden.cleanrickandmortyandroid.domain.model.characters.toModel
import com.maden.cleanrickandmortyandroid.repository.FakeRickAndMortyRepository

class FakeGetCharactersUseCase(
    private val _repository: FakeRickAndMortyRepository
) {

    suspend fun execute(page: Int): DataResource<CharacterResponseModel> {
        val response = _repository.getCharacters(page = page)

        if (response is DataResource.Error) {
            return DataResource.Error(
                message = response.message ?: "Unknown Error"
            )
        }

        if (response.data == null) {
            return DataResource.Error(
                message = response.message ?: "Response data null"
            )
        }

        return DataResource.Success(
            data = response.data!!.toModel()
        )
    }

}