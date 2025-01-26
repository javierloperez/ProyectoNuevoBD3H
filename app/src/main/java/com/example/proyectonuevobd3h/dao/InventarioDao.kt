package com.example.proyectonuevobd3h.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.proyectonuevobd3h.modelo.PuntuacionPeliculas

@Dao
interface InventarioDao {
    @Query("select * from PuntuacionPeliculas where id = :id")
    suspend fun obtenerPuntuacion(id:String): PuntuacionPeliculas

    @Query("select * from PuntuacionPeliculas order by puntuacion DESC")
    suspend fun obtenerPuntuaciones(): List<PuntuacionPeliculas>

    @Insert
    suspend fun insertarPuntuacion(puntuacionPelis: PuntuacionPeliculas)

    @Update
    suspend fun actualizarPuntuacion(puntuacionPelis: PuntuacionPeliculas)

    @Delete
    suspend fun eliminarPuntuacion(puntuacionPelis: PuntuacionPeliculas)
}