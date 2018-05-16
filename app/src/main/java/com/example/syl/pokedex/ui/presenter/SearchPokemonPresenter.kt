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
            searchPokemon(num)
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

            view?.showPokemon(pokemon)
        } else {
            view?.showError(result?.second?.message)
        }
    }

    fun returnTypeOfPokemon(pokemon: Pokemon?): String {
        var typeText: String = ""

        var types = pokemon?.types
        for (type: Type in types!!) {
            if (type.type?.name?.equals(TypesLanguage.fire.toString())!!) {
                typeText += " " + context.getString(R.string.fire)
            }
            if (type.type?.name?.equals(TypesLanguage.normal.toString())!!) {
                typeText += " " + context.getString(R.string.normal)
            }
            if (type.type?.name?.equals(TypesLanguage.fighting.toString())!!) {
                typeText += " " + context.getString(R.string.fight)
            }
            if (type.type?.name?.equals(TypesLanguage.flying.toString())!!) {
                typeText += " " + context.getString(R.string.fly)
            }
            if (type.type?.name?.equals(TypesLanguage.poison.toString())!!) {
                typeText += " " + context.getString(R.string.poison)
            }
            if (type.type?.name?.equals(TypesLanguage.ground.toString())!!) {
                typeText += " " + context.getString(R.string.ground)
            }
            if (type.type?.name?.equals(TypesLanguage.rock.toString())!!) {
                typeText += " " + context.getString(R.string.rock)
            }
            if (type.type?.name?.equals(TypesLanguage.grass.toString())!!) {
                typeText += " " + context.getString(R.string.grass)
            }
            if (type.type?.name?.equals(TypesLanguage.bug.toString())!!) {
                typeText += " " + context.getString(R.string.bug)
            }
            if (type.type?.name?.equals(TypesLanguage.ghost.toString())!!) {
                typeText += " " + context.getString(R.string.ghost)
            }
            if (type.type?.name?.equals(TypesLanguage.steel.toString())!!) {
                typeText += " " + context.getString(R.string.steel)
            }
            if (type.type?.name?.equals(TypesLanguage.water.toString())!!) {
                typeText += " " + context.getString(R.string.water)
            }
            if (type.type?.name?.equals(TypesLanguage.electric.toString())!!) {
                typeText += " " + context.getString(R.string.electric)
            }
            if (type.type?.name?.equals(TypesLanguage.psychic.toString())!!) {
                typeText += " " + context.getString(R.string.psychic)
            }
            if (type.type?.name?.equals(TypesLanguage.ice.toString())!!) {
                typeText += " " + context.getString(R.string.ice)
            }
            if (type.type?.name?.equals(TypesLanguage.dragon.toString())!!) {
                typeText += " " + context.getString(R.string.dragon)
            }
            if (type.type?.name?.equals(TypesLanguage.dark.toString())!!) {
                typeText += " " + context.getString(R.string.dark)
            }
            if (type.type?.name?.equals(TypesLanguage.fairy.toString())!!) {
                typeText += " " + context.getString(R.string.fairy)
            }
        }

        return typeText
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