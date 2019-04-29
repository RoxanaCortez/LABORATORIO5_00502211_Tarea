package com.roxyapps.roxana.clase.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import com.roxyapps.roxana.clase.AppConstants
import com.roxyapps.roxana.clase.R
import com.roxyapps.roxana.clase.adapters.PokemonAdapter
import com.roxyapps.roxana.clase.fragments.MainContentFragment
import com.roxyapps.roxana.clase.fragments.MainListFragment
import com.roxyapps.roxana.clase.utilies.NetworkUtils
import com.roxyapps.roxana.clase.models.Data_pokemon
import com.roxyapps.roxana.clase.models.Pokemon
import com.roxyapps.roxana.clase.models.TypeResponse
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

//conexion
class MainActivity : AppCompatActivity(), MainListFragment.SearchNewPokemonListener {
    override fun manageLandscapeItemClick(pokemon: Data_pokemon) {
        ""
    }


    //refenecia de objetos
    //fragment
    private lateinit var mainFragment : MainListFragment
    private lateinit var mainContentFragment: MainContentFragment

    private lateinit var viewAdapter: PokemonAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var pokemonList: ArrayList<Data_pokemon> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        pokemonList = savedInstanceState?.getParcelableArrayList(AppConstants.dataset_saveinstance_key) ?: ArrayList()

        initMainFragment()
        //initRecyclerview()
        //initSearchButton()

    }
    //Fragment
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.dataset_saveinstance_key, pokemonList)
        super.onSaveInstanceState(outState)
    }
    fun initRecyclerview(){
        viewManager = LinearLayoutManager(this)
        viewAdapter = PokemonAdapter(pokemonList, { pokemonItem: Data_pokemon -> pokemonItemCliked(pokemonItem) })

        //seteo del adaptador
        rv_pokemon_li.apply{
            setHasFixedSize(true) //de antemano el tamaño del alto y ancho no cambiara
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }
    //Fragment
    fun initMainFragment(){
        mainFragment = MainListFragment.newInstance(pokemonList)

        val resource = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            R.id.main_fragment
        else {
            mainContentFragment = MainContentFragment.newInstance(Pokemon())
            changeFragment(R.id.land_main_cont_fragment, mainContentFragment)

            R.id.land_main_fragment
        }

        changeFragment(resource, mainFragment)
    }
    fun initSearchButton()= bt_buscar.setOnClickListener {
        if(!et_tipo.text.toString().isEmpty()){
            FetchPokermon().execute(et_tipo.text.toString())
        }
    }
    fun addPokemonToList2(pokemon:Data_pokemon){
        pokemonList.add(pokemon)
        viewAdapter.changeList(pokemonList)
        Log.d("Number", pokemonList.size.toString())
    }
    //Fragment
    fun addPokemonToList(pokemon: Data_pokemon) {
        pokemonList.add(pokemon)
        mainFragment.updatePokemonsAdapter(pokemonList)
        Log.d("Number", pokemonList.size.toString())
    }

    fun addPokemonToList(pokemon:List<Data_pokemon>){
        pokemonList.addAll(pokemon)
        viewAdapter.changeList(pokemonList)
        Log.d("Number", pokemonList.size.toString())
    }
    fun pokemonItemCliked(pokemon:Data_pokemon){
        startActivity(Intent(this, PokemonViewer::class.java).putExtra("CLAVIER", pokemon.url))
    }
    //Fragment
    override fun searchPokemon(pokemonName: String) {
        FetchPokermon().execute(pokemonName)
    }
    //Fragment
    override fun managePortraitItemClick(pokemon: Data_pokemon) {
        val pokemonBundle = Bundle()
        //pokemonBundle.putParcelable("POKE", pokemon)
        //startActivity(Intent(this, PokemonViewer::class.java).putExtras(pokemonBundle))
        startActivity(Intent(this, PokemonViewer::class.java).putExtra("CLAVIER", pokemon.url))
    }
    //Fragment
    private fun changeFragment(id: Int, frag: Fragment){ supportFragmentManager.beginTransaction().replace(id, frag).commit() }
    //Para Fragment
    override fun manageLandscapeItemClick(pokemon: Pokemon) {
        mainContentFragment = MainContentFragment.newInstance(pokemon)
        changeFragment(R.id.land_main_cont_fragment, mainContentFragment)
    }

    private inner class FetchPokermon:AsyncTask<String,Void,String>(){
        override fun doInBackground(vararg params: String): String {

            if(params.isEmpty())return ""
            val pokemonType = params[0]
            val pokemonUrl = NetworkUtils.buildSearUrl("type",pokemonType)

            return try{
                NetworkUtils.getResponseFromHttpUrl(pokemonUrl)
            }catch (e:IOException){
                e.printStackTrace()
                ""
            }
        }

        override fun onPostExecute(pokemonInfo: String) {
            super.onPostExecute(pokemonInfo)
            if(!pokemonInfo.isEmpty()){
                Log.d("JSON", pokemonInfo)
                val pokemonJson = Gson().fromJson<TypeResponse>(pokemonInfo,TypeResponse::class.java)

                if(pokemonJson.pokemon!!.size > 0) {
                    for ( pokemon in pokemonJson.pokemon!!) {
                        addPokemonToList(pokemon.pokemon)
                    }
                }else{
                    Snackbar.make(main_ll, "No existe pokemon de este tipo en la base",Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

}

