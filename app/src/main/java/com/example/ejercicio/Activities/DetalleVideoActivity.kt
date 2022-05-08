package com.example.ejercicio.Activities

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ejercicio.Apis.VideosApis
import com.example.ejercicio.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView


class DetalleVideoActivity : YouTubeBaseActivity(){

    lateinit var progressDialog: ProgressDialog
    lateinit var context : Context
    lateinit var ytPlayer: YouTubePlayerView
    private lateinit var videosApis : VideosApis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_video)

        val idVideo = intent.getSerializableExtra("link") as String
        inicializarVariables()
        reproducirvideo(idVideo)

    }

    fun inicializarVariables()
    {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.mensaje_espera))
        progressDialog.setCancelable(false)

        videosApis = VideosApis()
        videosApis.context = this
        context = this
        asignarControles()
    }

    fun asignarControles()
    {
        ytPlayer = findViewById<View>(R.id.ytPlayer) as YouTubePlayerView
    }


    fun reproducirvideo(link:String)
    {
        ytPlayer.initialize(
            getString(R.string.api_youtube),
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) {
                    youTubePlayer.loadVideo(link)
                    youTubePlayer.play()
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    Toast.makeText(applicationContext, "Ocurrio un error al reproducir el video", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}