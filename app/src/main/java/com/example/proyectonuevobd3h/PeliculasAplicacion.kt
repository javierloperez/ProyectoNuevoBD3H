package com.example.proyectonuevobd3h

import android.app.Application
import com.example.proyectonuevobd3h.datos.ContenedorApp
import com.example.proyectonuevobd3h.datos.PeliculasContenedorApp

class PeliculasAplicacion: Application() {
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = PeliculasContenedorApp(this)
    }
}