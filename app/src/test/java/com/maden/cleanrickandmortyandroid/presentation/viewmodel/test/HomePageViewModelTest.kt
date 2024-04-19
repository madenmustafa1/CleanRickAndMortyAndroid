package com.maden.cleanrickandmortyandroid.presentation.viewmodel.test

import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.presentation.viewmodel.FakeHomePageViewModel
import com.maden.cleanrickandmortyandroid.repository.FakeRickAndMortyRepository
import com.maden.cleanrickandmortyandroid.use_case.FakeGetCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomePageViewModelTest {

    private lateinit var viewModel: FakeHomePageViewModel
    private lateinit var fakeGetCharactersUseCase: FakeGetCharactersUseCase

    @Before
    fun setup() {
        fakeGetCharactersUseCase = FakeGetCharactersUseCase(FakeRickAndMortyRepository())
        viewModel = FakeHomePageViewModel(fakeGetCharactersUseCase)
    }

    @Test
    fun `getCharactersData returns Error when pageCount is less than 1`() = runBlocking {
        // Given
        val pageCount = 0

        // When
        val result = viewModel.getCharactersData(pageCount)

        // Then
        assertTrue(result is DataResource.Error)
        assertEquals("The number of pages should not be less than 1", (result as DataResource.Error).message)
    }

    @Test
    fun `getCharactersData returns Success when pageCount is valid`() = runBlocking {
        // Given
        val pageCount = 1

        // When
        val result = viewModel.getCharactersData(pageCount)

        // Then
        assertTrue(result is DataResource.Success)
        assertTrue(result.data != null)
    }

    @Test
    fun `fetchNextCharactersData resets currentPage and fetches data`() = runBlocking {
        // Given
        viewModel.getCharactersData()

        // When
        viewModel.fetchNextCharactersData()

        // Then
        assertEquals(1, viewModel._currentPage)
        assertTrue(viewModel._charactersData is DataResource.Success)
    }

    @Test
    fun `fetchNextCharacter fetches next character successfully`() = runBlocking {
        // Given
        viewModel.getCharactersData()
        val initialCharacterIndex = viewModel._currentCharacterIndex

        // When
        viewModel.fetchNextCharacter()

        // Then
        assertTrue(viewModel._charactersData is DataResource.Success)
        assertEquals(initialCharacterIndex + 1, viewModel._currentCharacterIndex)
    }
}
