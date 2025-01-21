package com.example.proyectonuevobd3h.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PuntacionPeliculas")
class PuntuacionPelis(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val puntuacion: Double = 0.0

)
