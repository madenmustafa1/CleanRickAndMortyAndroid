package com.maden.cleanrickandmortyandroid.presentation.home_page

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.domain.model.characters.CharacterModel
import com.maden.cleanrickandmortyandroid.domain.model.characters.CharacterResponseModel
import com.maden.cleanrickandmortyandroid.domain.use_cases.GetCharactersUseCase
import com.maden.easy_bitmap.EasyBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val _getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private var _characterResponseModel: CharacterResponseModel? = null
    private val _charactersData = MutableLiveData<DataResource<CharacterModel>>()
    val charactersData: LiveData<DataResource<CharacterModel>> = _charactersData

    fun getCharactersData(pageCount: Int = 1) {
        viewModelScope.launch(Dispatchers.IO + _exceptionHandler) {
            if (pageCount < 1) {
                _charactersData.value = DataResource.Error(
                    message = "The number of pages should not be less than 1"
                )
                return@launch
            }

            _charactersData.postValue(DataResource.Loading())
            val response = _getCharactersUseCase.execute(page = pageCount)

            if (response.data == null) {
                _charactersData.postValue(DataResource.Error(message = "Something went wrong"))
                return@launch
            }

            _characterResponseModel = response.data
            _currentCharacterIndex = 0

            _charactersData.postValue(DataResource.Success(data = response.data.characterList[_currentCharacterIndex]))
        }
    }

    private var _currentPage = 1
    private fun fetchNextCharactersData() {
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

    private var _currentCharacterIndex = 0
    fun fetchNextCharacter() {
        _currentCharacterIndex++
        if (charactersData.value !is DataResource.Success || _characterResponseModel == null) {
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
        _charactersData.postValue(DataResource.Success(data = characterList[_currentCharacterIndex]))
    }

    fun downloadBitmap(
        url: String,
        exception: (exception: Exception) -> Unit = { },
        bitmap: (bitmap: Bitmap?) -> Unit
    ) {
        EasyBitmap().downloadImage(
            url = url,
            exception = exception,
            bitmap = bitmap
        )
    }

    private val _exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _charactersData.value = DataResource.Error(
            message = "An error occurred! ${exception.localizedMessage}"
        )
    }

}