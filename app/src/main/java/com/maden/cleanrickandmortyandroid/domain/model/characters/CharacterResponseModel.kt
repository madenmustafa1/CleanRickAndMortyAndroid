package com.maden.cleanrickandmortyandroid.domain.model.characters

import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model.CharacterListResponseDto

data class CharacterResponseModel(
    val page: Int,
    val count: Int,
    val characterList: ArrayList<CharacterModel>
)

fun CharacterListResponseDto.toModel(): CharacterResponseModel {
    val characterList: ArrayList<CharacterModel> = arrayListOf()

    for(i in this.results) {
        characterList.add(
            CharacterModel(
                characterPhoto = i.image,
                characterName = i.name,
                status = i.status,
                lastKnowLocation = LocationModel(
                    name = i.location.name,
                    url = i.location.url
                )
            )
        )
    }

    return CharacterResponseModel(
        characterList = characterList,
        count = this.info.count,
        page = this.info.pages,
    )
}