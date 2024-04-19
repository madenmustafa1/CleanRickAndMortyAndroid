package com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.model

data class InfoDto(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)