package com.example.syl.pokedex.global.model

internal class Sprites(var front_default: String, var front_shiny: String) {

    override fun toString(): String {
        return "Sprites{" +
                "front_default='" + front_default + '\''.toString() +
                ", front_shiny='" + front_shiny + '\''.toString() +
                '}'.toString()
    }
}