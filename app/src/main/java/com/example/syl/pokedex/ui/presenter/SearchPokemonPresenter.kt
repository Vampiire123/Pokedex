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
package com.example.syl.pokedex.ui.presenter

import android.content.Context
import com.example.syl.pokedex.R
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.model.Type
import com.example.syl.pokedex.model.TypesLanguage
import com.example.syl.pokedex.usecase.GetPokemon
import kotlinx.coroutines.experimental.async
import java.util.Random
import java.util.Locale

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
            typesToLoad(pokemon)
            view?.showPokemon(pokemon)
        } else {
            view?.showError(result?.second?.message)
        }
    }

    suspend fun onRandomButtonClicked() {
        getRandomPokemon()
    }

    suspend fun onSearchButtonClicked(num: String) {
        view?.hideKeyboard()
        view?.showLoading()

        if (num == "") {
            view?.showError(context.getString(R.string.et_search_empty))
        } else {
            searchPokemon(num.toLowerCase())
        }
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
            typesToLoad(pokemon)
            view?.showPokemon(pokemon)
        } else {
            view?.showError(result?.second?.message)
        }
    }

    fun typesToLoad(pokemon: Pokemon?) {
        val types = pokemon?.types
        val languageType = TypesLanguage.values()

        for (type: Type in types!!) {
            languageType.forEach {
                if (type.name?.equals(it.value)!!) {
                    if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                        type.urlImage = it.imageUrlEnglish
                    } else {
                        type.urlImage = it.imageUrlSpanish
                    }
                }
            }
        }

        pokemon.types = types
    }

    fun onBackPressed() {
        view?.hideTypeTable()
    }

    fun onTypeTableButtonClicked() {
        view?.showTypeTable()
    }

    interface View {
        fun hideTypeTable()
        fun showTypeTable()
        fun hideKeyboard()
        fun showLoading()
        fun showPokemon(pokemon: Pokemon?)
        fun showError(msg: String?)
    }
}