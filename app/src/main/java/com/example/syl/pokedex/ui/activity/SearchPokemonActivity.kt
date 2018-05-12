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
package com.example.syl.pokedex.ui.activity

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.*
import com.example.syl.pokedex.R

import com.example.syl.pokedex.datasource.api.GetPokemonApiImpl
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.ui.presenter.SearchPokemonPresenter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient


class SearchPokemonActivity : BaseActivity(), SearchPokemonPresenter.View {

    var searchPokemonPresenter: SearchPokemonPresenter? = null

    var cl_pokemon: ConstraintLayout? = null
    var et_num_pokemon: EditText? = null
    var iv_front_default: ImageView? = null
    var iv_front_shiny: ImageView? = null
    var tv_name: TextView? = null
    var pb_sprite_default: ProgressBar? = null
    var pb_sprite_shiny: ProgressBar? = null
    var tv_type: TextView? = null
    var wv_type_table: WebView? = null
    lateinit var btn_refresh: Button
    lateinit var btn_search: Button
    lateinit var btn_type_table: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher_foreground)

        cl_pokemon = findViewById(R.id.ll_pokemon)
        et_num_pokemon = findViewById(R.id.et_num_pokemon)
        btn_search = findViewById(R.id.btn_search)
        iv_front_default = findViewById(R.id.iv_sprite_default)
        iv_front_shiny = findViewById(R.id.iv_sprite_shiny)
        tv_name = findViewById(R.id.tv_name)
        pb_sprite_default = findViewById(R.id.pb_sprite_default)
        pb_sprite_shiny = findViewById(R.id.pb_sprite_shiny)
        btn_refresh = findViewById(R.id.btn_refresh)
        tv_type = findViewById(R.id.tv_type)
        btn_type_table = findViewById(R.id.btn_type_table)
        wv_type_table = findViewById(R.id.wv_type_table)

        wv_type_table?.settings?.setSupportZoom(true)
        wv_type_table?.settings?.builtInZoomControls = true
        wv_type_table?.settings?.loadWithOverviewMode = true
        wv_type_table?.settings?.useWideViewPort = true

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
        btn_type_table.setOnClickListener({
            searchPokemonPresenter?.onTypeTableButtonClicked()
        })

        launch(CommonPool) {
            searchPokemonPresenter?.initialize()
        }
    }

    override fun showPokemon(pokemon: Pokemon?) = runOnUiThread {
        tv_name?.text = pokemon?.name
        tv_type?.text = pokemon?.types?.toString()

        Picasso.with(this).load(pokemon?.sprites?.frontDefault).into(iv_front_default)
        Picasso.with(this).load(pokemon?.sprites?.frontShiny).into(iv_front_shiny)
    }

    override fun showLoading() = runOnUiThread {
        iv_front_shiny?.visibility = View.GONE
        iv_front_default?.visibility = View.GONE
        pb_sprite_default?.visibility = View.VISIBLE
        pb_sprite_shiny?.visibility = View.VISIBLE

        tv_name?.text = getString(R.string.loading)
        tv_type?.text = getString(R.string.loading)
    }

    override fun hideLoading() = runOnUiThread {
        iv_front_shiny?.visibility = View.VISIBLE
        iv_front_default?.visibility = View.VISIBLE
        pb_sprite_default?.visibility = View.GONE
        pb_sprite_shiny?.visibility = View.GONE
    }

    override fun showError(msg: String?) = runOnUiThread {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        tv_type?.text = "-"
        tv_name?.text = "-"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv_front_default?.setImageDrawable(getDrawable(R.drawable.silueta_interrogante))
            iv_front_shiny?.setImageDrawable(getDrawable(R.drawable.silueta_interrogante))
        }
    }

    override fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun showTypeTable() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        var webSettings = wv_type_table?.settings
        webSettings?.javaScriptEnabled = true
        wv_type_table?.webViewClient = WebViewClient()
        wv_type_table?.loadUrl(getString(R.string.url_type_table))

        wv_type_table?.visibility = View.VISIBLE
        cl_pokemon?.visibility = View.GONE
    }

    override fun hideTypeTable() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        cl_pokemon?.visibility = View.VISIBLE
        wv_type_table?.visibility = View.GONE
    }

    override fun onBackPressed() {
        searchPokemonPresenter?.onBackPressed()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search_pokemon
    }
}