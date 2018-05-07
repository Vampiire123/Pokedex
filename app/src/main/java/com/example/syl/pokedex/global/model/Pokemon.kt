package com.example.syl.pokedex.global.model

class Pokemon internal constructor(var name: String, var sprites: Sprites) {

    override fun toString(): String {
        return "Pokemon{" +
                "name='" + name + '\''.toString() +
                ", sprites='" + sprites.toString() + '\''.toString() +
                '}'.toString()
    }
}
