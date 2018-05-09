package com.example.syl.pokedex.ui.activity

import android.os.Bundle
import com.example.syl.pokedex.R

import com.example.syl.pokedex.datasource.api.GetRandomPokemonApiImpl
import com.example.syl.pokedex.ui.presenter.MainPresenter

class MainActivity : BaseActivity() {

    var mainPresenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getRandomPokemonApiImpl = GetRandomPokemonApiImpl()
        mainPresenter = MainPresenter(this, getRandomPokemonApiImpl)

        mainPresenter?.initialize()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}