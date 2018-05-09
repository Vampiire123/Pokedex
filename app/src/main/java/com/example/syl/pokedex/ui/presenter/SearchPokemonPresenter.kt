package com.example.syl.pokedex.ui.presenter

import android.content.Context
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.usecase.GetRandomPokemon
import org.jetbrains.anko.doAsync

class SearchPokemonPresenter(val context: Context, val getRandomPokemon: GetRandomPokemon) : Presenter<SearchPokemonPresenter.View>() {

    override fun initialize() {
        doAsync {
            val pokemon: Pokemon? = getRandomPokemon.getRandomPokemon().first
            view?.showPokemon(pokemon)
        }
    }

    interface View {
        fun showPokemon(pokemon: Pokemon?)
    }
}