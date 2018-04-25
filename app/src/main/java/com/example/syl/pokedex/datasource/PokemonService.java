package com.example.syl.pokedex.datasource;

import com.example.syl.pokedex.global.model.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonService {

    @GET("1")
    Call<Pokemon> getRandomPokemon();
}