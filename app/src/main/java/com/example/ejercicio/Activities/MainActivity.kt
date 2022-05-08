package com.example.ejercicio.Activities

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.Apis.PeliculasApi
import com.example.ejercicio.Objects.Peliculas
import com.example.ejercicio.R
import com.example.ejercicio.RecyclerView.RecyclerViewPeliculas
import com.example.ejercicio.Responses.PeliculasResponse


class MainActivity : AppCompatActivity(), PeliculasApi.PeliculasCallback {

    private var listaPeliculas:MutableList<Peliculas> = ArrayList()
    private lateinit var mViewEmpleados : RecyclerViewPeliculas
    private lateinit var mRecyclerView : RecyclerView
    lateinit var context : Context
    var pagina = 0;
    private var tipoBusqueda = 1;
    var limite = false;
    private lateinit var peliculasApi : PeliculasApi
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarVariables()
        obtenerPeliculas(++pagina)
    }

    fun asignarControles(){
        mViewEmpleados = RecyclerViewPeliculas()
        mRecyclerView = findViewById(R.id.mainPeliculas)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        mViewEmpleados.RecyclerAdapter(listaPeliculas, this)
        mRecyclerView.adapter = mViewEmpleados

        //Cargar mas peliculas al llegar al fondo
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(!limite)
                        obtenerPeliculas(++pagina)
                }
            }
        })

        val buttonPopular: Button = findViewById(R.id.mainPopular)
        buttonPopular.setOnClickListener()
        {
            cambiarBusqueda(1)
        }

        val buttonCartelera: Button = findViewById(R.id.mainCartelera)
        buttonCartelera.setOnClickListener()
        {
            cambiarBusqueda(2)
        }

    }

    fun cambiarBusqueda(tipoBusquedaNuevo:Int)
    {
        if(tipoBusqueda != tipoBusquedaNuevo)
        {
            val tamanioLista = listaPeliculas.size;
            pagina = 0
            tipoBusqueda = tipoBusquedaNuevo
            listaPeliculas.clear()
            mViewEmpleados.notifyItemRangeRemoved(0,tamanioLista)
            obtenerPeliculas(++pagina)
        }

    }

    fun inicializarVariables()
    {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.mensaje_espera))
        progressDialog.setCancelable(false)

        peliculasApi = PeliculasApi()
        peliculasApi.context = this
        context = this
        asignarControles()
    }

    fun obtenerPeliculas(pagina:Int)
    {
        progressDialog.show()

        try{ peliculasApi.obtenerUltimasPeliculas(pagina,tipoBusqueda) }
        catch (ex:Exception)
        {
            progressDialog.dismiss()
            Toast.makeText(context, context.getString(R.string.mensaje_error_internet), Toast.LENGTH_LONG).show()
        }

    }


    override fun onSuccessResponse(result: PeliculasResponse)
    {
        var elementosActuales =  listaPeliculas.size
        if(pagina >= result.total_pages) limite = true;

        listaPeliculas.addAll(result.results.toMutableList())
        mViewEmpleados.RecyclerAdapter(listaPeliculas, this)
        mViewEmpleados.notifyItemRangeInserted(elementosActuales,listaPeliculas.size)

        progressDialog.dismiss()
    }

    override fun onFailureResponse()
    {
        Toast.makeText(context, context.getString(R.string.mensaje_error_generico), Toast.LENGTH_LONG).show()
        progressDialog.dismiss()
        pagina--
    }
}