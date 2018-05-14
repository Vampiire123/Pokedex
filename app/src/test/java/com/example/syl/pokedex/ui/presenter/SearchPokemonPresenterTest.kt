package com.example.syl.pokedex.ui.presenter

import android.content.Context
import com.example.syl.pokedex.R
import com.example.syl.pokedex.model.Pokemon
import com.example.syl.pokedex.model.Sprites
import com.example.syl.pokedex.usecase.GetPokemon
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchPokemonPresenterTest {

    lateinit var presenter: SearchPokemonPresenter

    @Mock
    lateinit var mockView: SearchPokemonPresenter.View

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockGetPokemon: GetPokemon

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = createAMockedPresenter()
    }

    @Test
    fun `should get a random pokemon on start`() {
        runBlocking {
            presenter.initialize()
        }

        verify(mockGetPokemon).getPokemon(any())
    }

    @Test
    fun `should get a random pokemon when random button is clicked`() {
        runBlocking {
            presenter.onRandomButtonClicked()
        }

        verify(mockGetPokemon).getPokemon(any())
    }

    @Test
    fun `should show loading when random button is clicked`() {
        runBlocking {
            presenter.onRandomButtonClicked()
        }

        verify(mockView).showLoading()
    }

    @Test
    fun `should show pokemon on success when random button is clicked`() {
        val pokemon = Pokemon("charmander", Sprites("url1", "url2"), mutableListOf())
        givenApiReturnsAPokemon(pokemon)

        runBlocking {
            presenter.onRandomButtonClicked()
        }

        verify(mockView).showPokemon(pokemon)
    }

    @Test
    fun `should show pokemon on success when start the app`() {
        val pokemon = Pokemon("charmander", Sprites("url1", "url2"), mutableListOf())
        givenApiReturnsAPokemon(pokemon)

        runBlocking {
            presenter.initialize()
        }

        verify(mockView).showPokemon(pokemon)
    }

    @Test
    fun `should show error when api doesn't return a random pokemon`() {
        givenApiReturnsError()

        runBlocking {
            presenter.initialize()
        }

        verify(mockView).showError("Error")
    }

    @Test
    fun `should show error when it's empty query and search button is clicked`() {
        givenMockedString()

        runBlocking {
            presenter.onSearchButtonClicked("")
        }

        verify(mockView).showError(any())
    }

    @Test
    fun `should hide keyboard when search button is clicked`() {
        runBlocking {
            presenter.onSearchButtonClicked("")
        }

        verify(mockView).hideKeyboard()
    }

    @Test
    fun `should show loading when search button is clicked`() {
        runBlocking {
            presenter.onSearchButtonClicked("")
        }

        verify(mockView).showLoading()
    }

    @Test
    fun `should show pokemon when introducing a pokemon's name and search button is clicked`() {
        givenApiReturnsAPokemon(Pokemon())

        runBlocking {
            presenter.onSearchButtonClicked("charmander")
        }

        verify(mockView).showPokemon(any())
    }

    @Test
    fun `should show error when introducing a non-exists name and search button is clicked`() {
        givenApiReturnsError()

        runBlocking {
            presenter.onSearchButtonClicked("charmander")
        }

        verify(mockView).showError("Error")
    }

    @Test
    fun `should hide table of types when back button is pressed`() {
        presenter.onBackPressed()

        verify(mockView).hideTypeTable()
    }

    @Test
    fun `should show table of types when TypeTable button is clicked`() {
        presenter.onTypeTableButtonClicked()

        verify(mockView).showTypeTable()
    }

    private fun givenMockedString() {
        whenever(mockContext.getString(R.string.et_search_empty)).thenReturn("Error")
    }

    private fun givenApiReturnsError() {
        whenever(mockGetPokemon.getPokemon(any())).thenReturn(Pair(null, Exception("Error")))
    }

    private fun givenApiReturnsAPokemon(pokemon: Pokemon) {
        whenever(mockGetPokemon.getPokemon(any())).thenReturn(Pair(pokemon, null))
    }

    fun createAMockedPresenter(): SearchPokemonPresenter {
        val searchPokemonPresenter = SearchPokemonPresenter(mockContext, mockGetPokemon)
        searchPokemonPresenter.view = mockView

        return searchPokemonPresenter
    }
}