package com.example.syl.pokedex.usecase

import com.example.syl.pokedex.model.Pokemon
import java.lang.Exception

interface GetRandomPokemon {
    fun getRandomPokemon(): Pair<Pokemon?, Exception?>
}