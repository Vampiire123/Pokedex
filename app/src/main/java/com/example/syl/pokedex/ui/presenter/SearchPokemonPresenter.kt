package com.example.syl.pokedex.ui.presenter

import android.content.Context
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.usecase.GetRandomPokemon
import kotlinx.coroutines.experimental.async

class SearchPokemonPresenter(val context: Context, val getRandomPokemon: GetRandomPokemon) : Presenter<SearchPokemonPresenter.View>() {

    override suspend fun initialize() {
        var pokemon: Pokemon? = null
        async {
            pokemon = getRandomPokemon.getRandomPokemon().first
        }.await()
        view?.showPokemon(pokemon)
    }

    interface View {
        fun showPokemon(pokemon: Pokemon?)
    }
}