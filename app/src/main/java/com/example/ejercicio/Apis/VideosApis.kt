package com.example.ejercicio.Apis

import android.content.Context
import com.example.ejercicio.Objects.Urls
import com.example.ejercicio.Responses.VideoResponse
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class VideosApis {

    lateinit var context: Context

    fun obtenerLinkVideo(peliculaId:Int){

        val peliculasInterface = context as VideosCallback
        val urls = Urls()

        val url = urls.url+urls.movie+peliculaId+urls.video+urls.api
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                peliculasInterface.onFailureVideoResponse()
            }
            override fun onResponse(call: Call, response: Response)
            {
                val body = response.body()?.string()
                if(body != null && body.isNotEmpty()) {
                    try
                    {
                        val gson = GsonBuilder().create()
                        val model = gson.fromJson(body, VideoResponse::class.java)

                        peliculasInterface.onSuccessVideoResponse(model)
                    }
                    catch(e:Exception)
                    {
                    }
                }
            }
        })
    }

    interface VideosCallback {
        fun onSuccessVideoResponse(result: VideoResponse)
        fun onFailureVideoResponse()
    }
}