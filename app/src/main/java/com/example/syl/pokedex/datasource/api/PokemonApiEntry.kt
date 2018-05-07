package com.example.syl.pokedex.datasource.api

import com.example.syl.pokedex.global.model.Pokemon
import com.example.syl.pokedex.global.model.Sprites

class PokemonApiEntry(
        var name: String? = "",
        var sprites: Sprites? = null
) {
    inline fun map(): Pokemon = Pokemon(
            name = name,
            sprites = sprites
    )
}