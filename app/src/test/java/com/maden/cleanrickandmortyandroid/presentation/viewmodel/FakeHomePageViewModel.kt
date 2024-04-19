package com.maden.cleanrickandmortyandroid.presentation.viewmodel

import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.domain.model.characters.CharacterModel
import com.maden.cleanrickandmortyandroid.domain.model.characters.CharacterResponseModel
import com.maden.cleanrickandmortyandroid.use_case.FakeGetCharactersUseCase

class FakeHomePageViewModel(
    private val _getCharactersUseCase: FakeGetCharactersUseCase
) {

    var _characterResponseModel: CharacterResponseModel? = null
    var _charactersData: DataResource<CharacterModel>? = null
    var _currentPage = 1
    var _currentCharacterIndex = 0

    suspend fun getCharactersData(pageCount: Int = 1): DataResource<CharacterModel> {
        if (pageCount < 1) {
            _charactersData = DataResource.Error(
                message = "The number of pages should not be less than 1"
            )
            return _charactersData!!
        }

        val response = _getCharactersUseCase.execute(page = pageCount)

        if (response.data == null) {
            _charactersData = DataResource.Error(message = "Something went wrong")
            return _charactersData!!
        }

        _characterResponseModel = response.data
        _currentCharacterIndex = 0
        _charactersData = DataResource.Success(data = response.data!!.characterList[_currentCharacterIndex])
        return _charactersData!!
    }

    suspend fun fetchNextCharactersData() {
        _currentPage++

        //Page control
        if (_characterResponseModel == null || (_characterResponseModel?.page ?: 1) < _currentPage) {
            _currentPage = 1
            getCharactersData(pageCount = 1)
            return
        }

        //Get next page
        getCharactersData(_currentPage)
    }

    suspend fun fetchNextCharacter() {
        _currentCharacterIndex++
        if (_charactersData !is DataResource.Success || _characterResponseModel == null) {
            fetchNextCharactersData()
            return
        }

        val characterList = _characterResponseModel!!.characterList
        if (characterList.size < _currentCharacterIndex) return

        //Last character
        if (characterList.size == _currentCharacterIndex) {
            fetchNextCharactersData()
            return
        }

        //Get new character
        _charactersData = DataResource.Success(data = characterList[_currentCharacterIndex])
    }

}