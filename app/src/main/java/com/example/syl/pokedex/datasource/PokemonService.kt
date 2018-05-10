package com.example.syl.pokedex.datasource

import com.example.syl.pokedex.datasource.api.PokemonApiEntry

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonService {

    @GET
    fun getPokemon(@Url num: String): Call<PokemonApiEntry>
}