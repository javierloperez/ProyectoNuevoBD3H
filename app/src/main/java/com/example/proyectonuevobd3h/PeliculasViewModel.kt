package com.example.proyectonuevobd3h

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proyectonuevobd3h.datos.PeliculasRepositorio

import com.example.proyectonuevobd3h.modelo.PuntuacionPelis
import kotlinx.coroutines.launch

sealed interface PeliculasUIState {
    data class ObtenerExitoTodos(val puntuacionPelis: List<PuntuacionPelis>) : PeliculasUIState
    data class ObtenerExito(val puntuacionPelis: PuntuacionPelis) : PeliculasUIState

    object CrearExito : PeliculasUIState
    object ActualizarExito : PeliculasUIState
    object Error : PeliculasUIState
    object Cargando : PeliculasUIState
}

class PeliculasViewModel(private val peliculasRepositorio: PeliculasRepositorio) : ViewModel() {

    var peliculasUiState: PeliculasUIState by mutableStateOf(PeliculasUIState.Cargando)
        private set

    var peliculaPulsada: PuntuacionPelis by mutableStateOf(PuntuacionPelis(0, 0.0))
        private set


    init {
        obtenerPuntuaciones()
    }

    fun obtenerPuntuaciones() {
        viewModelScope.launch {
            peliculasUiState = try {
                val lista = peliculasRepositorio.obtenerPuntuaciones()
                PeliculasUIState.ObtenerExitoTodos(lista)
            } catch (e: Exception) {
                PeliculasUIState.Error
            }
        }
    }

    fun obtenerPuntuacion(id: Int) {
        viewModelScope.launch {
            peliculasUiState = try {
                val puntuacion = peliculasRepositorio.obtenerPuntuacion(id)
                peliculaPulsada = puntuacion
                PeliculasUIState.ObtenerExito(puntuacion)
            } catch (e: Exception) {
                PeliculasUIState.Error
            }

        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as PeliculasAplicacion)
                val peliculasRepositorio = aplicacion.contenedor.pelisRepositorio
                PeliculasViewModel(peliculasRepositorio = peliculasRepositorio)
            }
        }
    }
}