package com.maden.cleanrickandmortyandroid.repository

import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.RickAndMortyServiceApiInterface
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model.CharacterListResponseDto
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model.InfoDto
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model.LocationDto
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model.OriginDto
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model.ResultDto


class FakeRickAndMortyRepository : RickAndMortyServiceApiInterface {
    override suspend fun getCharacters(page: Int): DataResource<CharacterListResponseDto> {
        return DataResource.Success(
            CharacterListResponseDto(
                info = InfoDto(count = 0, next = "", pages = 0, prev = ""),
                results = fakeList()
            )
        )
    }

    private fun fakeList(): ArrayList<ResultDto> {
        val list = arrayListOf<ResultDto>()
        for (i in 0..100) {
            list.add(
                ResultDto(
                    created = "2022-01-01T12:00:00Z",
                    episode = listOf("S01E01", "S01E02"),
                    gender = "Male",
                    id = 1,
                    image = "https://example.com/image.jpg",
                    location = LocationDto(
                        name = "Earth",
                        url = "https://example.com/earth"
                    ),
                    name = "Rick Sanchez",
                    origin = OriginDto(
                        name = "Earth",
                        url = "https://example.com/earth"
                    ),
                    species = "Human",
                    status = "Alive",
                    type = "Humanoid",
                    url = "https://example.com/rick"
                )
            )
        }
        return list
    }
}
