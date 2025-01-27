package com.example.proyectonuevobd3h.datos

import android.content.Context
import com.example.proyectonuevobd3h.conexion.InventarioBaseDatos
import com.example.proyectonuevobd3h.conexion.PeliculasServicioApi
import com.example.proyectonuevobd3h.conexion.UsuarioServicioApi
import com.example.proyectonuevobd3h.modelo.Usuario
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp{
    val peliculasRepositorio: PeliculasRepositorio
    val puntuacionRepositorio: PuntuacionRepositorio
    val usuarioRepositorio : UsuarioRepositorio
}

class PeliculasContenedorApp(private val context: Context): ContenedorApp{
    private val baseUrl = "http://10.0.2.2:3000"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioRetrofitPeliculas: PeliculasServicioApi by lazy {
        retrofit.create(PeliculasServicioApi::class.java)
    }
    private val servicioRetrofitUsuarios: UsuarioServicioApi by lazy {
        retrofit.create(UsuarioServicioApi::class.java)
    }
    override val peliculasRepositorio: PeliculasRepositorio by lazy  {
        ConexionPeliculasRepositorio(servicioRetrofitPeliculas)
    }
    override val puntuacionRepositorio: PuntuacionRepositorio by lazy  {
        ConexionPuntuacionRepositorio(InventarioBaseDatos.obtenerBaseDatos(context).inventarioDao())
    }

    override val usuarioRepositorio: UsuarioRepositorio by lazy {
        ConexionUsuarioRepositorio(servicioRetrofitUsuarios)
    }



}