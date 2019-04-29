package com.roxyapps.roxana.clase.fragments

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.roxyapps.roxana.clase.R
import com.roxyapps.roxana.clase.models.Data_pokemon
import com.roxyapps.roxana.clase.models.Pokemon
import com.roxyapps.roxana.clase.utilies.NetworkUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_content_fragment_layout.view.*
import kotlinx.android.synthetic.main.viewer_element_pokemon.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class MainContentFragment : Fragment() {
    var poke = Pokemon()

    companion object {
        fun newInstance(pokemon: Pokemon): MainContentFragment{
            val newFragment = MainContentFragment()
            newFragment.poke = pokemon
            return newFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.main_content_fragment_layout, container, false)

        bindData(view)

        return view
    }

    fun bindData(view: View){
        view.pokemon_title_main_content_fragment.text = poke.name
        view.id_main_content_fragment.text = poke.id.toString()
        view.weight_main_content_fragment.text = poke.weight
        view.height_main_content_fragment.text = poke.height
        view.type1_main_content_fragment.text = poke.fsttype
        view.type2_main_content_fragment.text = poke.sndtype
        Glide.with(view).load(poke.sprite)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.image_main_content_fragment)
    }

}