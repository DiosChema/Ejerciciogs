package com.example.ejercicio.Apis

import android.content.Context
import com.example.ejercicio.Objects.Urls
import com.example.ejercicio.Responses.PeliculasResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import okhttp3.*
import java.io.IOException

class PeliculasApi {

    lateinit var context: Context

    fun obtenerUltimasPeliculas(pagina:Int){

        var peliculasInterface = context as PeliculasCallback

        val urls = Urls()
        val url = urls.url+urls.lastMovies+urls.api+urls.pagina+pagina
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                peliculasInterface.onFailureResponse()
            }
            override fun onResponse(call: Call, response: Response)
            {
                val body = response.body()?.string()
                if(body != null && body.isNotEmpty()) {
                    try
                    {
                        val gson = GsonBuilder().create()
                        val model = gson.fromJson(body, PeliculasResponse::class.java)

                        peliculasInterface.onSuccessResponse(model)
                    }
                    catch(e:Exception)
                    {
                    }
                }
            }
        })
    }

    interface PeliculasCallback {
        fun onSuccessResponse(result: PeliculasResponse)
        fun onFailureResponse()
    }
}