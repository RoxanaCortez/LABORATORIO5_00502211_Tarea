package com.roxyapps.roxana.clase.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.roxyapps.roxana.clase.MyPokemonAdapter
import  com.roxyapps.roxana.clase.R
import com.roxyapps.roxana.clase.models.Data_pokemon
import com.roxyapps.roxana.clase.models.Pokemon
import kotlinx.android.synthetic.main.cardview_pokemon.view.*
import kotlinx.android.synthetic.main.lista_elemento_pokemon.view.*


class PokemonAdapter(var items:List<Data_pokemon>, val clickListener: (Data_pokemon)-> Unit):RecyclerView.Adapter<PokemonAdapter.ViewHolder>(),
    MyPokemonAdapter {
    override fun changeDataSet(newDataSet: List<Data_pokemon>) {
        this.items = newDataSet
        notifyDataSetChanged()
    }

    //segundo paso
    //Infla recyclerview con la lista de elementos
    // lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_elemento_pokemon, parent, false)
        return ViewHolder(view)
    }
    //tercer paso
    //nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
    override fun getItemCount()= items.size

    // cuarto paso
    //se encarga de coger cada una de las posiciones de la lista de y
    // pasarlas a la clase ViewHolder para que esta pinte todos los valores.
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)=viewHolder.bind(items[position], clickListener)

    fun changeList(items: List<Data_pokemon>){
        this.items = items

        notifyDataSetChanged()
    }

    //Es lo que crea primero
    // Obtiene los valores de la de elementos igualando a la data asignada
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        fun bind(pokemon:Data_pokemon, clickLister:(Data_pokemon)->Unit) = with(itemView){
            tv_nombre.text = pokemon.name
            tv_tipo.text = pokemon.url
            this.setOnClickListener {clickLister(pokemon)}
        }
    }
}