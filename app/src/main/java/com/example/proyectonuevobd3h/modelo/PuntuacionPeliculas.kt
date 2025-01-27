package com.example.proyectonuevobd3h.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PuntuacionPeliculas")
data class PuntuacionPeliculas(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val puntuacion: Double = 0.0

)
