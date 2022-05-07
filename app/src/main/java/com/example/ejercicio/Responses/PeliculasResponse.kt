package com.example.ejercicio.Responses

import com.example.ejercicio.Objects.Peliculas
import java.io.Serializable

data class PeliculasResponse(
    var results:List<Peliculas>,
    var page:Int,
    var total_pages:Int
): Serializable