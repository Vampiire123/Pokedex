package com.example.syl.pokedex.datasource

import com.example.syl.pokedex.datasource.api.PokemonApiEntry

import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {

    @GET("1")
    fun randomPokemon(): Call<PokemonApiEntry>
}