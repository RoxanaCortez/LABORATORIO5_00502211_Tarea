package com.roxyapps.roxana.clase.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roxyapps.roxana.clase.AppConstants
import com.roxyapps.roxana.clase.MyPokemonAdapter
import com.roxyapps.roxana.clase.MyPokemonAdapter2
import com.roxyapps.roxana.clase.R
import com.roxyapps.roxana.clase.adapters.MovieSimpleListAdapter
import com.roxyapps.roxana.clase.adapters.PokemonAdapter
import com.roxyapps.roxana.clase.models.Data_pokemon
import com.roxyapps.roxana.clase.models.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainListFragment : Fragment() {
    private lateinit var  pokemons :ArrayList<Data_pokemon>
    private lateinit var pokemonsAdapter : MyPokemonAdapter
    var listenerTool :  SearchNewPokemonListener? = null

    companion object {
        fun newInstance(dataset : ArrayList<Data_pokemon>): MainListFragment{
            val newFragment = MainListFragment()
            newFragment.pokemons = dataset
            return newFragment
        }
    }

    interface SearchNewPokemonListener{
        fun searchPokemon(PokemonName: String)

        fun managePortraitItemClick(pokemon: Data_pokemon)

        fun manageLandscapeItemClick(pokemon: Data_pokemon)
        fun manageLandscapeItemClick(pokemon: Pokemon)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.activity_main, container, false)

        if(savedInstanceState != null) pokemons = savedInstanceState.getParcelableArrayList<Data_pokemon>(AppConstants.MAIN_LIST_KEY)!!

        initRecyclerView(resources.configuration.orientation, view)
        initSearchButton(view)

        return view
    }

    fun initRecyclerView(orientation:Int, container:View){
        val linearLayoutManager = LinearLayoutManager(this.context)

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            pokemonsAdapter = PokemonAdapter(pokemons, { pokemon:Data_pokemon->listenerTool?.managePortraitItemClick(pokemon)})
            container.rv_pokemon_li.adapter = pokemonsAdapter as PokemonAdapter
        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            pokemonsAdapter = MovieSimpleListAdapter(pokemons, {pokemon:Data_pokemon->listenerTool?.manageLandscapeItemClick(pokemon)})
            container.rv_pokemon_li.adapter = pokemonsAdapter as MovieSimpleListAdapter
        }

        container.rv_pokemon_li.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
    }

    fun initSearchButton(container:View) = container.bt_buscar.setOnClickListener {
        listenerTool?.searchPokemon(et_tipo.text.toString())
    }

    fun updatePokemonsAdapter(pokemonList: ArrayList<Data_pokemon>){ pokemonsAdapter.changeDataSet(pokemonList) }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SearchNewPokemonListener) {
            listenerTool = context
        } else {
            throw RuntimeException("Se necesita una implementación de  la interfaz")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.MAIN_LIST_KEY, pokemons)
        super.onSaveInstanceState(outState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerTool = null
    }
}