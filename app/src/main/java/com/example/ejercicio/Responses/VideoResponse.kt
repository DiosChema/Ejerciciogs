package com.example.ejercicio.Responses

import com.example.ejercicio.Objects.PeliculaVideo
import java.io.Serializable

data class VideoResponse(
    var id:Int,
    var results:List<PeliculaVideo>
): Serializable