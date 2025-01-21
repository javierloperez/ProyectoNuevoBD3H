package com.example.proyectonuevobd3h.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.proyectonuevobd3h.modelo.PuntuacionPelis

@Dao
interface InventarioDao {
    @Query("select * from PuntacionPeliculas where id = :id")
    suspend fun obtenerPuntuacion(id:Int): PuntuacionPelis

    @Query("select * from PuntacionPeliculas order by puntuacion DESC")
    suspend fun obtenerPuntuaciones(): List<PuntuacionPelis>

    @Insert
    suspend fun insertarPuntuacion(puntuacionPelis: PuntuacionPelis)

    @Update
    suspend fun actualizarPuntuacion(puntuacionPelis: PuntuacionPelis)

    @Delete
    suspend fun borrarPuntuacion(puntuacionPelis: PuntuacionPelis)
}