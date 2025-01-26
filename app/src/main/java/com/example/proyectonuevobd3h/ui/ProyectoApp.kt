package com.example.proyectonuevobd3h.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevobd3h.R
import com.example.proyectonuevobd3h.ui.pantallas.PantallaInicio
import com.example.proyectonuevobd3h.ui.pantallas.PantallaLogin

enum class Pantallas(@StringRes val titulo: Int) {
    Login(titulo = R.string.login),
    Inicio(titulo = R.string.peliculasTitulo),

}

@Composable
fun ProyectoApp(
    viewModel: PeliculasViewModel = viewModel(factory = PeliculasViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {

    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.Login.name
    )

    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = { navController.navigateUp() }
            )
        },

        ) { innerPadding ->

        val uiState = viewModel.peliculasUiState

        NavHost(
            navController = navController,
            startDestination = Pantallas.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Pantallas.Login.name) {
                PantallaLogin(
                    appUIState = uiState,
                    onUsuariosObtenidos = { viewModel.obtenerUsuario() },
                    onLoginPulsado = {
                        navController.navigate(Pantallas.Inicio.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }



            composable(route = Pantallas.Inicio.name) {
                PantallaInicio(
                    appUIState = uiState,
                    onPeliculasObtenidas = { viewModel.obtenerPeliculas() },
                    onVerPelicula = {
                        viewModel.actualizarPelicula(it.id, it)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit
) {

    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (puedeNavegarAtras) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.atras)
                    )
                }
            }
        },
    )

}
