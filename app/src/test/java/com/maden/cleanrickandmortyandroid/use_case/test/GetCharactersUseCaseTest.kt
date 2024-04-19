package com.maden.cleanrickandmortyandroid.use_case.test

import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.repository.FakeRickAndMortyRepository
import com.maden.cleanrickandmortyandroid.use_case.FakeGetCharactersUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCharactersUseCaseTest {

    private lateinit var _useCase: FakeGetCharactersUseCase

    @Before
    fun setup() {
        _useCase = FakeGetCharactersUseCase(FakeRickAndMortyRepository())
    }

    @Test
    fun `retrieve characters based on page count and pass successfully`() = runBlocking {
        // Given
        val page = 1

        // When
        val result = _useCase.execute(page = page)

        // Then
        assertTrue(result is DataResource.Success)
        assertTrue(result.data != null)
    }
}

