package com.example.syl.pokedex.ui.presenter

import android.content.Context
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.usecase.GetPokemon
import kotlinx.coroutines.experimental.async
import java.util.*

class SearchPokemonPresenter(val context: Context, val getPokemon: GetPokemon) : Presenter<SearchPokemonPresenter.View>() {

    override suspend fun initialize() {
        getRandomPokemon()
    }

    suspend fun searchPokemon(num: String) {
        var result: Pair<Pokemon?, Exception?>? = null
        async {
            result = getPokemon.getPokemon(num)
        }.await()
        if (result?.first != null) {
            val pokemon = result?.first
            view?.showPokemon(pokemon)
        } else {
            view?.showError(result?.second!!)
        }
    }

    suspend fun onRefreshButtonClicked() {
        getRandomPokemon()
    }

    suspend fun onSearchButtonClicked(num: String) {
        view?.showLoading()
        if (validNum(num)) {
            searchPokemon(num)
        } else {
            view?.showError(Exception("Write a valid num between 1 and 802"))
        }
        view?.hideLoading()
    }

    fun validNum(num: String) : Boolean {
        val number = Integer.parseInt(num)
        return number in 1..802
    }

    suspend fun getRandomPokemon() {
        view?.showLoading()
        val random = Random()
        val num: String = (random.nextInt(802 - 1) + 1).toString()
        var result: Pair<Pokemon?, Exception?>? = null
        async {
            result = getPokemon.getPokemon(num)
        }.await()
        if (result?.first != null) {
            val pokemon = result?.first
            view?.showPokemon(pokemon)
        } else {
            view?.showError(result?.second!!)
        }
        view?.hideLoading()
    }

    interface View {
        fun hideLoading()
        fun showLoading()
        fun showPokemon(pokemon: Pokemon?)
        fun showError(msg: Exception)
    }
}