package com.example.ejercicio.Objects

data class Urls(
    var url:String,
    var imagen:String,
    var lastMovies:String,
    var api:String
)
{
    constructor(): this(
        "https://api.themoviedb.org/3",
        "https://www.themoviedb.org/t/p/w300_and_h450_bestv2",
        "/discover/movie?primary_release_date.gte=2022-04-01&primary_release_date.lte=2022-04-01",
        "&api_key=7b63a641c032019f4bd1faa4e073ed75"
    )
}
