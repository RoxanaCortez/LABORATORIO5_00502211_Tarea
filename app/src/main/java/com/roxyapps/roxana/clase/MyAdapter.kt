package com.roxyapps.roxana.clase

import com.roxyapps.roxana.clase.models.Data_pokemon
import com.roxyapps.roxana.clase.models.Pokemon


object AppConstants{
    val dataset_saveinstance_key = "CLE"
    val MAIN_LIST_KEY = "key_list_pokemons"
}

interface MyPokemonAdapter {
    fun changeDataSet(newDataSet : List<Data_pokemon>)
}
interface MyPokemonAdapter2 {
    fun changeDataSet(newDataSet : List<Pokemon>)
}