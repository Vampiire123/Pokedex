/*
 * Copyright (C) 2018 Sylvia Domenech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.syl.pokedex.datasource.api

import com.example.syl.pokedex.datasource.PokemonService
import com.example.syl.pokedex.datasource.api.model.PokemonApiEntry
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.usecase.GetPokemon
import com.google.gson.JsonSyntaxException
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

class GetPokemonApiImpl : GetPokemon, ApiRequest {
    override fun getPokemon(num: String): Pair<Pokemon?, Exception?> {
        val httpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(EndPoint())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

        val service = retrofit.create<PokemonService>(PokemonService::class.java)
        val callSync = service.getPokemon(num)

        try {
            val response: Response<PokemonApiEntry> = callSync.execute()

            if (response.body() is PokemonApiEntry) {
                return Pair(response.body()?.toDomain(), null)
            } else if (response.errorBody() != null) {
                val jsonObject = JSONObject(response.errorBody()?.string())
                return Pair(null, Exception(jsonObject.getString("detail")))
            } else {
                return Pair(null, Exception("Unknown error"))
            }
        } catch (e: JsonSyntaxException) {
            return Pair(null, Exception(e.toString()))
        }
    }
}