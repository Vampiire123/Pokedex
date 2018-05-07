package com.example.syl.pokedex.datasource.api

import com.example.syl.pokedex.global.model.Sprites

class SpritesApiEntry(
        var front_default: String? = "",
        var front_shiny: String? = ""
) {
    inline fun Map(): Sprites = Sprites(
            frontDefault = front_default,
            frontShiny = front_shiny
    )
}