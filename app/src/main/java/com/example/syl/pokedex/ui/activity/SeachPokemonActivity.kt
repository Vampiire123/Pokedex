package com.example.syl.pokedex.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.syl.pokedex.R

import com.example.syl.pokedex.datasource.api.GetPokemonApiImpl
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.ui.presenter.SearchPokemonPresenter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch

class SeachPokemonActivity : BaseActivity(), SearchPokemonPresenter.View {

    var searchPokemonPresenter: SearchPokemonPresenter? = null

    var et_num_pokemon: EditText? = null
    var iv_front_default: ImageView? = null
    var iv_front_shiny: ImageView? = null
    var tv_name: TextView? = null
    var pb_sprite_default: ProgressBar? = null
    var pb_sprite_shiny: ProgressBar? = null
    lateinit var btn_refresh: Button
    lateinit var btn_search: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        et_num_pokemon = findViewById(R.id.et_num_pokemon)
        btn_search = findViewById(R.id.btn_search)
        iv_front_default = findViewById(R.id.iv_sprite_default)
        iv_front_shiny = findViewById(R.id.iv_sprite_shiny)
        tv_name = findViewById(R.id.tv_name)
        pb_sprite_default = findViewById(R.id.pb_sprite_default)
        pb_sprite_shiny = findViewById(R.id.pb_sprite_shiny)
        btn_refresh = findViewById(R.id.btn_refresh)

        searchPokemonPresenter = SearchPokemonPresenter(this, GetPokemonApiImpl())
        searchPokemonPresenter?.view = this

        btn_refresh.setOnClickListener({
            launch(CommonPool) {
                searchPokemonPresenter?.onRefreshButtonClicked()
            }
        })
        btn_search.setOnClickListener({
            launch(CommonPool) {
                searchPokemonPresenter?.onSearchButtonClicked(et_num_pokemon?.text.toString())
            }
        })

        launch(CommonPool) {
            searchPokemonPresenter?.initialize()
        }
    }

    override fun showPokemon(pokemon: Pokemon?) = runOnUiThread {
        tv_name?.text = pokemon?.name

        Picasso.with(this).load(pokemon?.sprites?.frontDefault).into(iv_front_default)
        Picasso.with(this).load(pokemon?.sprites?.frontShiny).into(iv_front_shiny)
    }

    override fun showLoading() = runOnUiThread {
        iv_front_shiny?.visibility = View.GONE
        iv_front_default?.visibility = View.GONE
        pb_sprite_default?.visibility = View.VISIBLE
        pb_sprite_shiny?.visibility = View.VISIBLE

        tv_name?.text = "Loading"
    }

    override fun hideLoading() = runOnUiThread {
        iv_front_shiny?.visibility = View.VISIBLE
        iv_front_default?.visibility = View.VISIBLE
        pb_sprite_default?.visibility = View.GONE
        pb_sprite_shiny?.visibility = View.GONE
    }

    override fun showError(msg: Exception) = runOnUiThread {
        Toast.makeText(this, msg.message, Toast.LENGTH_SHORT).show()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search_pokemon
    }
}