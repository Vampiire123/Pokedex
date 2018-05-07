package com.example.syl.pokedex.datasource

import com.example.syl.pokedex.global.model.Pokemon

import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {

    @get:GET("1")
    val randomPokemon: Call<Pokemon>
}