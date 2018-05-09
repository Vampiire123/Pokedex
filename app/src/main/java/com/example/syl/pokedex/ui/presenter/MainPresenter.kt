package com.example.syl.pokedex.ui.presenter

import android.content.Context
import com.example.syl.pokedex.datasource.api.GetRandomPokemonApiImpl
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.usecase.GetRandomPokemon
import org.jetbrains.anko.doAsync

class MainPresenter(val context: Context, val getRandomPokemon: GetRandomPokemon) : Presenter<MainPresenter.View, MainPresenter.Navigator>() {

    override fun initialize() {
        doAsync {
            val getRandomPokemon = GetRandomPokemonApiImpl()
            val pokemon: Pokemon? = getRandomPokemon.getRandomPokemon().first
            println("name: ${pokemon?.name}, ${pokemon?.sprites?.frontShiny}, ${pokemon?.sprites?.frontShiny}")
        }
    }

    interface View {

    }

    interface Navigator {

    }
}