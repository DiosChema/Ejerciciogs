package com.example.ejercicio.Activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.Apis.PeliculasApi
import com.example.ejercicio.Objects.Peliculas
import com.example.ejercicio.Objects.Urls
import com.example.ejercicio.R
import com.example.ejercicio.RecyclerView.RecyclerViewPeliculas
import com.example.ejercicio.Responses.PeliculasResponse
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), PeliculasApi.PeliculasCallback {

    var listaTmp:MutableList<Peliculas> = ArrayList()
    lateinit var mViewEmpleados : RecyclerViewPeliculas
    lateinit var mRecyclerView : RecyclerView
    //lateinit var globalVariable: GlobalClass
    lateinit var context : Context
    lateinit var activity: Activity
    var pagina = 0;
    var limite = false;
    lateinit var peliculasApi : PeliculasApi
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarVariables()
        obtenerUltimasPeliculas(++pagina)
    }

    fun crearRecyclerView(){
        mViewEmpleados = RecyclerViewPeliculas()
        mRecyclerView = findViewById(R.id.mainPeliculas)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        mViewEmpleados.RecyclerAdapter(listaTmp, this)
        mRecyclerView.adapter = mViewEmpleados

        //Cargar mas peliculas al llegar al fondo
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(!limite)
                        obtenerUltimasPeliculas(++pagina)
                }
            }
        })
    }

    fun inicializarVariables()
    {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.mensaje_espera))
        progressDialog.setCancelable(false)

        peliculasApi = PeliculasApi()
        peliculasApi.context = this
        crearRecyclerView()
    }

    fun obtenerUltimasPeliculas(pagina:Int)
    {
        progressDialog.show()
        peliculasApi.obtenerUltimasPeliculas(pagina)
    }


    override fun onSuccessResponse(result: PeliculasResponse)
    {
        if(pagina >= result.total_pages) limite = true;
        runOnUiThread {
            listaTmp.addAll(result.results.toMutableList())
            mViewEmpleados.RecyclerAdapter(listaTmp, this)
            mViewEmpleados.notifyDataSetChanged()
        }
        progressDialog.dismiss()
    }

    override fun onFailureResponse()
    {
        runOnUiThread{ Toast.makeText(context, context.getString(R.string.mensaje_error), Toast.LENGTH_LONG).show() }
        progressDialog.dismiss()
    }
}