package com.example.syl.pokedex.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.syl.pokedex.R

import com.example.syl.pokedex.datasource.api.GetRandomPokemonApiImpl
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.ui.presenter.SearchPokemonPresenter
import com.squareup.picasso.Picasso

class SeachPokemonActivity : BaseActivity(), SearchPokemonPresenter.View {

    var mainPresenter: SearchPokemonPresenter? = null

    var et_num_pokemon: EditText? = null
    var btn_search: Button? = null
    var iv_front_default: ImageView? = null
    var iv_front_shiny: ImageView? = null
    var tv_name: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        et_num_pokemon = findViewById(R.id.et_num_pokemon)
        btn_search = findViewById(R.id.btn_search)
        iv_front_default = findViewById(R.id.iv_sprite_default)
        iv_front_shiny = findViewById(R.id.iv_sprite_shiny)
        tv_name = findViewById(R.id.tv_name)

        val getRandomPokemonApiImpl = GetRandomPokemonApiImpl()
        mainPresenter = SearchPokemonPresenter(this, getRandomPokemonApiImpl)
        mainPresenter?.view = this

        mainPresenter?.initialize()
        //Picasso.with(this).load("http://imgsv.imaging.nikon.com/lineup/lens/zoom/normalzoom/af-s_dx_18-140mmf_35-56g_ed_vr/img/sample/img_01.jpg").into(iv_front_default)
    }

    override fun showPokemon(pokemon: Pokemon?) = runOnUiThread {
        tv_name?.text = pokemon?.name

        Picasso.with(this).load(pokemon?.sprites?.frontDefault).into(iv_front_default)
        Picasso.with(this).load(pokemon?.sprites?.frontShiny).into(iv_front_shiny)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search_pokemon
    }
}