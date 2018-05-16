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
        var types = pokemon?.types

        for (type: Type in types!!) {
            if (type.type?.name?.equals(TypesLanguage.Fire.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Fire.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Fire.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Normal.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Normal.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Normal.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Fighting.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Fighting.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Fighting.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Flying.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Flying.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Flying.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Poison.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Poison.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Poison.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Ground.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Ground.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Ground.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Rock.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Rock.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Rock.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Grass.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Grass.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Grass.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Bug.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Bug.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Bug.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Ghost.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Ghost.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Ghost.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Steel.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Steel.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Steel.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Water.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Water.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Water.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Electric.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Electric.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Electric.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Psychic.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Psychic.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Psychic.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Ice.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Ice.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Ice.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Dragon.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Dragon.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Dragon.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Dark.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Dark.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Dark.imageUrlSpanish
                }
            }
            if (type.type?.name?.equals(TypesLanguage.Fairy.value)!!) {
                if (Locale.getDefault().displayLanguage == Locale.ENGLISH.displayLanguage) {
                    type.type?.urlImage = TypesLanguage.Fairy.imageUrlEnglish
                } else {
                    type.type?.urlImage = TypesLanguage.Fairy.imageUrlSpanish
                }
            }
        }

        pokemon?.types = types
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