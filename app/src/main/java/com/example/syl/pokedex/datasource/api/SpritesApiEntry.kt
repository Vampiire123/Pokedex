package com.example.syl.pokedex.datasource.api

import com.example.syl.pokedex.model.Sprites

class SpritesApiEntry(
        var front_default: String? = "",
        var front_shiny: String? = ""
) {
    fun toDomain(): Sprites = Sprites(
            frontDefault = front_default,
            frontShiny = front_shiny
    )
}