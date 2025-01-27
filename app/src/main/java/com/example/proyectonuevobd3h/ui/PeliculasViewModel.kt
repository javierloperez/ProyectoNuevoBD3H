package com.example.proyectonuevobd3h.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proyectonuevobd3h.PeliculasAplicacion
import com.example.proyectonuevobd3h.datos.PeliculasRepositorio
import com.example.proyectonuevobd3h.datos.PuntuacionRepositorio
import com.example.proyectonuevobd3h.datos.UsuarioRepositorio
import com.example.proyectonuevobd3h.modelo.Peliculas
import com.example.proyectonuevobd3h.modelo.PuntuacionPeliculas
import com.example.proyectonuevobd3h.modelo.Usuario
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PeliculasUIState {

    data class ObtenerExitoPeliculas(val peliculas: List<Peliculas>) : PeliculasUIState
    data class ObtenerExitoUsuario(val usuario: List<Usuario>) : PeliculasUIState


    object CrearExito : PeliculasUIState
    object ActualizarExito : PeliculasUIState
    object Error : PeliculasUIState
    object Cargando : PeliculasUIState
    object EliminarExito : PeliculasUIState
}

sealed interface PuntuacionesUIState {


    data class ObtenerExitoPuntuacion(val puntuacionPelis: PuntuacionPeliculas) :
        PuntuacionesUIState

    object CrearExito : PuntuacionesUIState
    object ActualizarExito : PuntuacionesUIState
    object Error : PuntuacionesUIState
    object Cargando : PuntuacionesUIState
}

class PeliculasViewModel(
    private val puntuacionRepositorio: PuntuacionRepositorio,
    private val peliculasRepositorio: PeliculasRepositorio,
    private val usuarioRepositorio: UsuarioRepositorio
) : ViewModel() {

    var peliculasUiState: PeliculasUIState by mutableStateOf(PeliculasUIState.Cargando)
        private set

    var puntuacionesUIState: PuntuacionesUIState by mutableStateOf(PuntuacionesUIState.Cargando)
        private set

    var puntuacionPulsada: PuntuacionPeliculas by mutableStateOf(PuntuacionPeliculas("", 0.0))
        private set

    var peliculaPulsada: Peliculas by mutableStateOf(Peliculas("", "", "", "", "", 0))
        private set

    fun actualizarPeliculaPulsada(peliculas: Peliculas, ) {
        peliculaPulsada = peliculas
        peliculaPulsada= peliculaPulsada.copy(
            visualizaciones = peliculaPulsada.visualizaciones+1
        )
        actualizarPelicula(peliculaPulsada.id,peliculaPulsada)
    }

    fun actualizarPuntuacionPulsada(puntuacion: Double) {
        puntuacionPulsada = puntuacionPulsada.copy(
            puntuacion = puntuacion
        )
        actualizarPuntuacion(puntuacionPulsada)
    }

    init {
        obtenerUsuario()

    }

    fun obtenerPeliculas() {
        viewModelScope.launch {
            peliculasUiState = PeliculasUIState.Cargando
            peliculasUiState = try {
                val lista = peliculasRepositorio.obtenerPeliculas()
                PeliculasUIState.ObtenerExitoPeliculas(lista)
            } catch (e: IOException) {
                PeliculasUIState.Error
            } catch (e: HttpException) {
                PeliculasUIState.Error
            }
        }
    }

    fun obtenerUsuario() {
        viewModelScope.launch {
            peliculasUiState = try {
                val lista = usuarioRepositorio.obtenerUsuario()
                PeliculasUIState.ObtenerExitoUsuario(lista)
            } catch (e: IOException) {
                PeliculasUIState.Error
            } catch (e: HttpException) {
                PeliculasUIState.Error
            }
        }
    }

    fun actualizarPelicula(id: String, peliculas: Peliculas) {
        viewModelScope.launch {
            peliculasUiState = PeliculasUIState.Cargando
            peliculasUiState = try {
                val peliculaActualizada = peliculasRepositorio.actualizarPelicula(
                    id = id,
                    peliculas = peliculas
                )
                PeliculasUIState.ActualizarExito
            } catch (e: Exception) {
                PeliculasUIState.Error
            }
        }
    }


    fun obtenerPuntuacion(id: String) {
        viewModelScope.launch {
            puntuacionesUIState = try {
                val puntuacion = puntuacionRepositorio.obtenerPuntuacion(id)
                puntuacionPulsada = puntuacion
                PuntuacionesUIState.ObtenerExitoPuntuacion(puntuacion)
            } catch (e: Exception) {
                val puntuacionNueva = PuntuacionPeliculas(
                    id,
                    0.0
                )
                puntuacionRepositorio.insertarPuntuacion(
                    puntuacionPelis = puntuacionNueva
                )
                puntuacionPulsada = puntuacionNueva
                PuntuacionesUIState.ObtenerExitoPuntuacion(puntuacionNueva)
            }

        }
    }


    fun actualizarPuntuacion(puntuacionPelis: PuntuacionPeliculas) {
        viewModelScope.launch {
            puntuacionesUIState = try {
                puntuacionRepositorio.actualizarPuntuacion(puntuacionPelis)
                PuntuacionesUIState.ActualizarExito
            } catch (e: Exception) {
                PuntuacionesUIState.Error
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as PeliculasAplicacion)
                val puntuacionRepositorio = aplicacion.contenedor.puntuacionRepositorio
                val peliculasRepositorio = aplicacion.contenedor.peliculasRepositorio
                val usuarioRepositorio = aplicacion.contenedor.usuarioRepositorio
                PeliculasViewModel(
                    puntuacionRepositorio = puntuacionRepositorio,
                    peliculasRepositorio = peliculasRepositorio,
                    usuarioRepositorio = usuarioRepositorio
                )
            }
        }
    }
}