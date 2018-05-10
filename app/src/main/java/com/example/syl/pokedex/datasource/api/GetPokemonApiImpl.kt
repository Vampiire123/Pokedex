package com.example.syl.pokedex.datasource.api

import com.example.syl.pokedex.datasource.PokemonService
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.usecase.GetPokemon
import com.google.gson.JsonSyntaxException
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class GetPokemonApiImpl: GetPokemon {
    override fun getPokemon(num: String): Pair<Pokemon?, Exception?> {
        val httpClient = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

        val service = retrofit.create<PokemonService>(PokemonService::class.java)
        val callSync = service.getPokemon(num)

        try {
            val response: Response<PokemonApiEntry> = callSync.execute()

            if (response.body() is PokemonApiEntry) {
                return Pair(response.body()?.toDomain(), null)
            } else if (response.errorBody() != null){
                return Pair(null, Exception(response.errorBody().toString()))
            } else {
                return Pair(null, Exception("Unknown error"))
            }
        } catch (e: JsonSyntaxException) {
            return Pair(null, Exception(e.toString()))
        }
    }
}