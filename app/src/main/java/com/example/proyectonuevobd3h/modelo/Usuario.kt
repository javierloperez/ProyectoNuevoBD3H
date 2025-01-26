package com.example.proyectonuevobd3h.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Usuario (
    @SerialName(value = "id")
    val id:String = "",
    @SerialName(value = "user")
    val user:String ="",
    @SerialName(value = "correo")
    val correo:String =""
)