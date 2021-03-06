package com.example.ejercicio.Apis

import android.content.Context
import com.example.ejercicio.Objects.Urls
import com.example.ejercicio.Responses.PeliculasResponse
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class PeliculasApi {

    lateinit var context: Context

    fun obtenerPeliculas(pagina:Int, tipoPelicula:Int){

        val peliculasInterface = context as PeliculasCallback
        val urls = Urls()

        val tipoPeliculaApi = when(tipoPelicula)
        {
            1 -> urls.popular
            2 -> urls.nowPlaying
            else -> ""
        }

        val url = urls.url+tipoPeliculaApi+urls.api+urls.pagina+pagina
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                peliculasInterface.onFailurePeliculasResponse()
            }
            override fun onResponse(call: Call, response: Response)
            {
                val body = response.body()?.string()
                if(body != null && body.isNotEmpty()) {
                    try
                    {
                        val gson = GsonBuilder().create()
                        val model = gson.fromJson(body, PeliculasResponse::class.java)

                        peliculasInterface.onSuccessPeliculasResponse(model)
                    }
                    catch(e:Exception)
                    {
                    }
                }
            }
        })
    }

    interface PeliculasCallback {
        fun onSuccessPeliculasResponse(result: PeliculasResponse)
        fun onFailurePeliculasResponse()
    }
}