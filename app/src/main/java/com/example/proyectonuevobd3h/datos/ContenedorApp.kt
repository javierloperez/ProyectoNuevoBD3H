package com.example.proyectonuevobd3h.datos

import android.content.Context
import com.example.proyectonuevobd3h.conexion.InventarioBaseDatos

interface ContenedorApp{
    val pelisRepositorio: PeliculasRepositorio
}

class PeliculasContenedorApp(private val context: Context): ContenedorApp{

    override val pelisRepositorio: PeliculasRepositorio by lazy  {
        ConexionPeliculasRepositorio(InventarioBaseDatos.obtenerBaseDatos(context).inventarioDao())
    }
}