package com.example.proyectonuevobd3h.datos

import com.example.proyectonuevobd3h.dao.InventarioDao
import com.example.proyectonuevobd3h.modelo.PuntuacionPelis

interface PeliculasRepositorio{
    suspend fun obtenerPuntuacion(id:Int): PuntuacionPelis
    suspend fun obtenerPuntuaciones(): List<PuntuacionPelis>
    suspend fun insertarPuntuacion(puntuacionPelis: PuntuacionPelis)
    suspend fun actualizarPuntuacion(puntuacionPelis: PuntuacionPelis)
    suspend fun borrarPuntuacion(puntuacionPelis: PuntuacionPelis)
}

    class ConexionPeliculasRepositorio(
        private val inventarioDao: InventarioDao
    ): PeliculasRepositorio{
        override suspend fun obtenerPuntuacion(id: Int): PuntuacionPelis = inventarioDao.obtenerPuntuacion(id)
        override suspend fun obtenerPuntuaciones(): List<PuntuacionPelis> = inventarioDao.obtenerPuntuaciones()
        override suspend fun insertarPuntuacion(puntuacionPelis: PuntuacionPelis) = inventarioDao.insertarPuntuacion(puntuacionPelis)
        override suspend fun actualizarPuntuacion(puntuacionPelis: PuntuacionPelis) = inventarioDao.actualizarPuntuacion(puntuacionPelis)
        override suspend fun borrarPuntuacion(puntuacionPelis: PuntuacionPelis) = inventarioDao.borrarPuntuacion(puntuacionPelis)

    }