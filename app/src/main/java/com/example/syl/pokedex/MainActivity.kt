package com.example.syl.pokedex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.syl.pokedex.datasource.PokemonService
import com.example.syl.pokedex.global.model.Pokemon

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val httpClient = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

        val service = retrofit.create<PokemonService>(PokemonService::class.java)
        val callSync = service.randomPokemon

        callSync.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                val pokemon = response.body()
                println(pokemon!!.toString())
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                println(t)
            }
        })
    }
}