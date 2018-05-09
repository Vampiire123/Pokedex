package com.example.syl.pokedex.ui.presenter

abstract class Presenter<T1> {
    open fun initialize() {}
    open fun resume() {}
    open fun pause() {}
    open fun destroy() {
        view = null
    }

    var view : T1? = null
}