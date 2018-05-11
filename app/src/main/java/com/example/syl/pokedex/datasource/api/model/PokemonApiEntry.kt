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
package com.example.syl.pokedex.datasource.api.model

import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.model.Type

class PokemonApiEntry(
        var name: String? = "",
        var sprites: SpritesApiEntry? = null,
        var types: List<TypeApiEntry>? = null
) {
    fun toDomain(): Pokemon = Pokemon(
            name = name,
            sprites = sprites?.toDomain(),
            types = parseTypes()
    )

    private fun parseTypes(): List<Type> {
        val result = types?.map {
            it.toDomain()
        }

        return result ?: emptyList()
    }
}