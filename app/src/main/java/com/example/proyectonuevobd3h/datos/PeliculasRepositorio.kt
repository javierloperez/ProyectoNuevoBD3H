package com.example.proyectonuevobd3h.datos

import com.example.proyectonuevobd3h.conexion.PeliculasServicioApi
import com.example.proyectonuevobd3h.conexion.UsuarioServicioApi
import com.example.proyectonuevobd3h.dao.InventarioDao
import com.example.proyectonuevobd3h.modelo.Peliculas
import com.example.proyectonuevobd3h.modelo.PuntuacionPeliculas
import com.example.proyectonuevobd3h.modelo.Usuario

interface PuntuacionRepositorio {
    suspend fun obtenerPuntuacion(id: String): PuntuacionPeliculas
    suspend fun obtenerPuntuaciones(): List<PuntuacionPeliculas>
    suspend fun insertarPuntuacion(puntuacionPelis: PuntuacionPeliculas)
    suspend fun actualizarPuntuacion(puntuacionPelis: PuntuacionPeliculas)
    suspend fun eliminarPuntuacion(puntuacionPelis: PuntuacionPeliculas)

}

interface PeliculasRepositorio {
    suspend fun obtenerPeliculas(): List<Peliculas>
    suspend fun actualizarPelicula(id: String, peliculas: Peliculas): Peliculas
}

interface UsuarioRepositorio {
    suspend fun obtenerUsuario(): List<Usuario>
}

class ConexionPuntuacionRepositorio(
    private val inventarioDao: InventarioDao,
) : PuntuacionRepositorio {
    override suspend fun obtenerPuntuacion(id: String): PuntuacionPeliculas =
        inventarioDao.obtenerPuntuacion(id)

    override suspend fun obtenerPuntuaciones(): List<PuntuacionPeliculas> =
        inventarioDao.obtenerPuntuaciones()

    override suspend fun insertarPuntuacion(puntuacionPelis: PuntuacionPeliculas) =
        inventarioDao.insertarPuntuacion(puntuacionPelis)

    override suspend fun actualizarPuntuacion(puntuacionPelis: PuntuacionPeliculas) =
        inventarioDao.actualizarPuntuacion(puntuacionPelis)

    override suspend fun eliminarPuntuacion(puntuacionPelis: PuntuacionPeliculas) =
        inventarioDao.eliminarPuntuacion(puntuacionPelis)
}

class ConexionPeliculasRepositorio(
    private val peliculasServicioApi: PeliculasServicioApi
) : PeliculasRepositorio {
    override suspend fun obtenerPeliculas(): List<Peliculas> =
        peliculasServicioApi.obtenerPeliculas()

    override suspend fun actualizarPelicula(id: String, peliculas: Peliculas): Peliculas =
        peliculasServicioApi.actualizarPelicula(id, peliculas)
}

class ConexionUsuarioRepositorio(
    private val usuarioServicioApi: UsuarioServicioApi
) : UsuarioRepositorio {
    override suspend fun obtenerUsuario(): List<Usuario> =
        usuarioServicioApi.obtenerUsuario()
}