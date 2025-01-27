package com.example.proyectonuevobd3h.modelo

import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Peliculas(

    @SerialName(value = "id")
    val id: String = "",
    @SerialName(value = "nombre")
    val titulo: String = "",
    @SerialName(value = "descripcion")
    val descripcion: String = "",
    @SerialName(value = "duracion")
    val duracion: String = "",
    @SerialName(value = "imagen")
    val imagen: String = "",
    @SerialName(value = "visualizaciones")
    val visualizaciones: Int = 0
)