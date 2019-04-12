package com.roxyapps.roxana.clase.utilies

import android.net.Uri
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


object NetworkUtils {
    val POKEMON_API_BASE_URL="https://pokeapi.co/api/v2/"
    val POKEMON_INFO="type"

    fun buildSearUrl(type: String): URL {
        val builUri = Uri.parse(POKEMON_API_BASE_URL)
            .buildUpon()
            .appendPath(POKEMON_INFO)
            .appendPath(type)
            .build()

        return try{
            Log.d("URL", builUri.toString())
            URL(builUri.toString())
        }catch (e: MalformedURLException){
            URL("")
        }
    }
    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url:URL):String{
        val urlConnection = url.openConnection() as HttpURLConnection
        try{
            val `in` = urlConnection.inputStream

            val scanner = Scanner (`in`)

            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if(hasInput){
                scanner.next()
            }else{
                ""
            }
        }finally {
            urlConnection.disconnect()
        }
    }

}