package com.maden.cleanrickandmortyandroid.domain.use_cases

import android.net.http.HttpException
import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.service.RickAndMortyRepository
import com.maden.cleanrickandmortyandroid.domain.model.characters.CharacterResponseModel
import com.maden.cleanrickandmortyandroid.domain.model.characters.toModel
import java.lang.Exception
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val _repository: RickAndMortyRepository
) {

    /**
     * @param page -> page to list from api
     * @sample execute(page = 1)
     * @throws NullPointerException
     * @throws TypeCastException
     * @throws Exception
     * @throws HttpException
     * @return CharacterResponseModel? or Exception Message
     */
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
            data = response.data.toModel()
        )
    }
}