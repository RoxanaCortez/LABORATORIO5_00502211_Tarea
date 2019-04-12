package com.roxyapps.roxana.clase

import android.net.Network
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.roxyapps.roxana.clase.R
import com.roxyapps.roxana.clase.PokemonAdapter
import com.roxyapps.roxana.clase.utilies.NetworkUtils
import com.roxyapps.roxana.clase.models.Data_pokemon
import com.roxyapps.roxana.clase.models.TypeResponse
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.URL

//conexion
class MainActivity : AppCompatActivity() {

    //refenecia de objetos
    private  lateinit var viewAdapter: PokemonAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var pokemonList: ArrayList<Data_pokemon> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerview()
        initSearchButton()

    }
    fun initRecyclerview(){
        viewManager = LinearLayoutManager(this)
        viewAdapter = PokemonAdapter(pokemonList,{pokemonItem:Data_pokemon->pokemonItemCliked(pokemonItem)})

        //seteo del adaptador
        rv_pokemon_li.apply{
            setHasFixedSize(true) //de antemano el tamaño del alto y ancho no cambiara
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }
    fun initSearchButton()= bt_buscar.setOnClickListener {
        if(!et_tipo.text.toString().isEmpty()){
            FetchPokermon().execute(et_tipo.text.toString())
        }
    }
    fun addPokemonToList(pokemon:Data_pokemon){
        pokemonList.add(pokemon)
        viewAdapter.changeList(pokemonList)
        Log.d("Number", pokemonList.size.toString())
    }

    fun addPokemonToList(pokemon:List<Data_pokemon>){
        pokemonList.addAll(pokemon)
        viewAdapter.changeList(pokemonList)
        Log.d("Number", pokemonList.size.toString())
    }
    private fun pokemonItemCliked(item:Data_pokemon){}

    private inner class FetchPokermon:AsyncTask<String,Void,String>(){
        override fun doInBackground(vararg params: String): String {

            if(params.isEmpty())return ""
            val pokemonType = params[0]
            val pokemonUrl = NetworkUtils.buildSearUrl(pokemonType)

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

