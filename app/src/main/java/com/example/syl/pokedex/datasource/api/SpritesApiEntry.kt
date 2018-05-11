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

import com.example.syl.pokedex.model.Sprites

class SpritesApiEntry(
        var front_default: String? = "",
        var front_shiny: String? = ""
) {
    fun toDomain(): Sprites = Sprites(
            frontDefault = front_default,
            frontShiny = front_shiny
    )
}