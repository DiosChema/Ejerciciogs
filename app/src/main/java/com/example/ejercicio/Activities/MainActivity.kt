package com.example.ejercicio.Activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.Objects.Peliculas
import com.example.ejercicio.Objects.Urls
import com.example.ejercicio.R
import com.example.ejercicio.RecyclerView.RecyclerViewPeliculas
import com.example.ejercicio.Responses.PeliculasResponse
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var listaTmp:MutableList<Peliculas> = ArrayList()
    lateinit var mViewEmpleados : RecyclerViewPeliculas
    lateinit var mRecyclerView : RecyclerView
    //lateinit var globalVariable: GlobalClass
    lateinit var context : Context
    lateinit var activity: Activity
    var pagina = 0;
    var limite = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        activity = this

        crearRecyclerView()
        obtenerUltimasPeliculas(++pagina)
    }

    fun obtenerUltimasPeliculas(pagina:Int){
        val urls = Urls()
        val url = urls.url+urls.lastMovies+urls.api+urls.pagina+pagina
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val client = OkHttpClient()
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.mensaje_espera))
        progressDialog.setCancelable(false)
        progressDialog.show()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                progressDialog.dismiss()
                runOnUiThread{ Toast.makeText(context, context.getString(R.string.mensaje_error), Toast.LENGTH_LONG).show() }
            }
            override fun onResponse(call: Call, response: Response)
            {
                val body = response.body()?.string()
                if(body != null && body.isNotEmpty()) {
                    try
                    {
                        val gson = GsonBuilder().create()
                        val model = gson.fromJson(body, PeliculasResponse::class.java)

                        if(pagina >= model.total_pages) limite = true;

                        runOnUiThread {
                            listaTmp.addAll(model.results.toMutableList())
                            mViewEmpleados.RecyclerAdapter(listaTmp, context)
                            mViewEmpleados.notifyDataSetChanged()
                        }
                    }
                    catch(e:Exception)
                    {
                    }
                }
                progressDialog.dismiss()
            }
        })
    }

    fun crearRecyclerView(){
        mViewEmpleados = RecyclerViewPeliculas()
        mRecyclerView = findViewById(R.id.mainPeliculas)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        mViewEmpleados.RecyclerAdapter(listaTmp, this)
        mRecyclerView.adapter = mViewEmpleados


        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(!limite) obtenerUltimasPeliculas(++pagina)
                }
            }
        })
    }
}