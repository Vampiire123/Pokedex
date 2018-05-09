package com.example.syl.pokedex.ui.presenter

abstract class Presenter<T1,T2> {
    open fun initialize() {}
    open fun resume() {}
    open fun pause() {}
    open fun destroy() {}

    var view : T1? = null
    var navigator: T2? = null
}