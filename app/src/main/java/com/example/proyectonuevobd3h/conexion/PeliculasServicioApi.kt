package com.example.proyectonuevobd3h.conexion

import com.example.proyectonuevobd3h.modelo.Peliculas
import com.example.proyectonuevobd3h.modelo.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PeliculasServicioApi {
    @GET("peliculas")
    suspend fun obtenerPeliculas(): List<Peliculas>

    @PUT("peliculas/{id}")
    suspend fun actualizarPelicula(
        @Path("id") id: String,
        @Body peliculas: Peliculas
    ): Peliculas


}

interface UsuarioServicioApi {
    @GET("usuarios")
    suspend fun obtenerUsuario(): List<Usuario>

}