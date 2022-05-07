package com.example.ejercicio.RecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.Objects.Peliculas
import com.example.ejercicio.Objects.Urls
import com.example.ejercicio.R
import com.squareup.picasso.Picasso

class RecyclerViewPeliculas : RecyclerView.Adapter<RecyclerViewPeliculas.ViewHolder>() {

    var Peliculas: MutableList<Peliculas>  = ArrayList()
    lateinit var context: Context

    fun RecyclerAdapter(articulos : MutableList<Peliculas>, context: Context){
        this.Peliculas = articulos
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Peliculas[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.item_peliculas,
                parent,
                false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return Peliculas.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val urls = Urls()
        val imagen = view.findViewById(R.id.itemPeliculas_imagen) as ImageView

        val titulo = view.findViewById(R.id.itemPeliculas_titulo) as TextView
        val descripcion = view.findViewById(R.id.itemPeliculas_descripcion) as TextView
        val lenguaje = view.findViewById(R.id.itemPeliculas_lenguaje) as TextView
        val popularidad = view.findViewById(R.id.itemPeliculas_popularidad) as TextView

        fun bind(articulo: Peliculas) {

            titulo.text = articulo.title
            descripcion.text = articulo.overview
            lenguaje.text = articulo.release_date
            popularidad.text = articulo.popularity

            imagen.loadUrl(urls.imagen+articulo.poster_path)
        }

        fun ImageView.loadUrl(url: String) {
            try {Picasso.with(context).load(url).into(this)}
            catch(e: Exception){}
        }
    }
}