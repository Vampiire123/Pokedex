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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class SearchPokemonActivity : BaseActivity(), SearchPokemonPresenter.View {

    var searchPokemonPresenter: SearchPokemonPresenter? = null

    var clPokemon: ConstraintLayout? = null
    var etNumPokemon: EditText? = null
    var ivFrontDefault: ImageView? = null
    var ivFrontShiny: ImageView? = null
    var ivType1: ImageView? = null
    var ivType2: ImageView? = null
    var tvName: TextView? = null
    var wvTypeTable: WebView? = null
    lateinit var btnRefresh: Button
    lateinit var btnSearch: Button
    lateinit var btnTypeTable: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher_foreground)

        clPokemon = findViewById(R.id.ll_pokemon)
        etNumPokemon = findViewById(R.id.et_num_pokemon)
        btnSearch = findViewById(R.id.btn_search)
        ivFrontDefault = findViewById(R.id.iv_sprite_default)
        ivFrontShiny = findViewById(R.id.iv_sprite_shiny)
        ivType1 = findViewById(R.id.iv_type_1)
        ivType2 = findViewById(R.id.iv_type_2)
        tvName = findViewById(R.id.tv_name)
        btnRefresh = findViewById(R.id.btn_refresh)
        btnTypeTable = findViewById(R.id.btn_type_table)
        wvTypeTable = findViewById(R.id.wv_type_table)

        wvTypeTable?.settings?.setSupportZoom(true)
        wvTypeTable?.settings?.builtInZoomControls = true
        wvTypeTable?.settings?.loadWithOverviewMode = true
        wvTypeTable?.settings?.useWideViewPort = true

        searchPokemonPresenter = SearchPokemonPresenter(this, GetPokemonApiImpl())
        searchPokemonPresenter?.view = this

        btnRefresh.setOnClickListener({
            launch(CommonPool) {
                searchPokemonPresenter?.onRandomButtonClicked()
            }
        })
        btnSearch.setOnClickListener({
            launch(CommonPool) {
                searchPokemonPresenter?.onSearchButtonClicked(etNumPokemon?.text.toString())
            }
        })
        btnTypeTable.setOnClickListener({
            searchPokemonPresenter?.onTypeTableButtonClicked()
        })

        launch(CommonPool) {
            searchPokemonPresenter?.initialize()
        }
    }

    override fun showPokemon(pokemon: Pokemon?) = runOnUiThread {
        tvName?.text = pokemon?.name

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Picasso.with(this)
                    .load(pokemon?.types?.get(0)?.type?.urlImage)
                    .placeholder(getDrawable(R.drawable.loading))
                    .error(getDrawable(R.drawable.silueta_interrogante))
                    .into(ivType1)
            if (pokemon?.types?.size == 2) {
                ivType2?.visibility = View.VISIBLE
                Picasso.with(this)
                        .load(pokemon?.types?.get(1)?.type?.urlImage)
                        .placeholder(getDrawable(R.drawable.loading))
                        .error(getDrawable(R.drawable.silueta_interrogante))
                        .into(ivType2)
            } else {
                ivType2?.visibility = View.GONE
            }
            Picasso.with(this)
                    .load(pokemon?.sprites?.frontDefault)
                    .placeholder(getDrawable(R.drawable.loading))
                    .error(getDrawable(R.drawable.silueta_interrogante))
                    .into(ivFrontDefault)
            Picasso.with(this)
                    .load(pokemon?.sprites?.frontShiny)
                    .placeholder(getDrawable(R.drawable.loading))
                    .error(getDrawable(R.drawable.silueta_interrogante))
                    .into(ivFrontShiny)
        }
    }

    override fun showLoading() = runOnUiThread {
        tvName?.text = getString(R.string.loading)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivType1?.setImageDrawable(getDrawable(R.drawable.loading))
            ivType2?.setImageDrawable(getDrawable(R.drawable.loading))
            ivFrontShiny?.setImageDrawable(getDrawable(R.drawable.loading))
            ivFrontDefault?.setImageDrawable(getDrawable(R.drawable.loading))
        }
    }

    override fun showError(msg: String?) = runOnUiThread {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        tvName?.text = "-"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivType1?.setImageDrawable(getDrawable(R.drawable.silueta_interrogante))
            ivType2?.setImageDrawable(getDrawable(R.drawable.silueta_interrogante))
            ivFrontDefault?.setImageDrawable(getDrawable(R.drawable.silueta_interrogante))
            ivFrontShiny?.setImageDrawable(getDrawable(R.drawable.silueta_interrogante))
        }
    }

    override fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun showTypeTable() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        var webSettings = wvTypeTable?.settings
        webSettings?.javaScriptEnabled = true
        wvTypeTable?.webViewClient = WebViewClient()
        wvTypeTable?.loadUrl(getString(R.string.url_type_table))

        wvTypeTable?.visibility = View.VISIBLE
        clPokemon?.visibility = View.GONE
    }

    override fun hideTypeTable() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        clPokemon?.visibility = View.VISIBLE
        wvTypeTable?.visibility = View.GONE
    }

    override fun onBackPressed() {
        searchPokemonPresenter?.onBackPressed()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search_pokemon
    }
}