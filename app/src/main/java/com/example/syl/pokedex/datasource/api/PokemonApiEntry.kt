package com.example.syl.pokedex.datasource.api

import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.model.Sprites

class PokemonApiEntry(
        var name: String? = "",
        var sprites: SpritesApiEntry? = null
) {
    fun toDomain(): Pokemon = Pokemon(
            name = name,
            sprites = sprites?.toDomain()
    )
}