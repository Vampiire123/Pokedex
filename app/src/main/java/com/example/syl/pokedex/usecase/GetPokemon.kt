package com.example.syl.pokedex.usecase

import com.example.syl.pokedex.model.Pokemon
import java.lang.Exception

interface GetPokemon {
    fun getPokemon(num: String): Pair<Pokemon?, Exception?>
}