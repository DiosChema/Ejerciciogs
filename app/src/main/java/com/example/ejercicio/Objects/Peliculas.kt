package com.example.ejercicio.Objects

import java.io.Serializable

data class Peliculas(
    var poster_path:String,
    var title:String,
    var overview:String,
    var release_date:String,
    var popularity:String
): Serializable