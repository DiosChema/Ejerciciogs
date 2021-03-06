package com.example.ejercicio.Objects

data class Urls(
    var url:String,
    var imagen:String,
    var nowPlaying:String,
    var popular:String,
    var api:String,
    val pagina: String,
    val movie: String,
    val video: String
)
{


    constructor(): this(
        "https://api.themoviedb.org/3",
        "https://www.themoviedb.org/t/p/w300_and_h450_bestv2",
        "/movie/now_playing?",
        "/movie/popular?",
        "api_key=7b63a641c032019f4bd1faa4e073ed75",
        "&page=",
        "/movie/",
        "/videos?"
    )
}
