package com.example.ejercicio.Objects

import java.io.Serializable

data class Peliculas(
    var id:Int,
    var poster_path:String,
    var title:String,
    var overview:String,
    var release_date:String,
    var popularity:String
): Serializable