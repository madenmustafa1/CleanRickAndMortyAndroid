package com.maden.cleanrickandmortyandroid.domain.model.characters

import java.util.UUID

data class CharacterModel(
    val uuid: UUID = UUID.randomUUID(),
    val characterPhoto: String,
    val characterName: String,
    val status: String,
    val lastKnowLocation: LocationModel,
)