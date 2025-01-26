package com.example.proyectonuevobd3h.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.proyectonuevobd3h.R
import com.example.proyectonuevobd3h.modelo.Peliculas
import com.example.proyectonuevobd3h.modelo.PuntuacionPeliculas
import com.example.proyectonuevobd3h.ui.PeliculasUIState


@Composable
fun PantallaInicio(
    appUIState: PeliculasUIState,
    onPeliculasObtenidas: () -> Unit,
    modifier: Modifier = Modifier,
    onVerPelicula: (Int) -> Unit,
    onBuscarPuntuacion: (String) -> Unit,
    puntuacionObtenida: PuntuacionPeliculas,
    onPuntuacionActualizada: (Double) -> Unit,
) {


    when (appUIState) {
        is PeliculasUIState.Cargando -> PantallaCargando(modifier = modifier.fillMaxSize())
        is PeliculasUIState.Error -> PantallaError(modifier = modifier.fillMaxWidth())

        is PeliculasUIState.ObtenerExitoPeliculas -> {
            PantallaListarPeliculas(
                puntuacionObtenida = puntuacionObtenida,
                onPuntuacionActualizada = onPuntuacionActualizada,
                onBuscarPuntuacion = onBuscarPuntuacion,
                listaPeliculas = appUIState.peliculas,
                onVerPelicula = onVerPelicula,
                modifier = Modifier.fillMaxWidth(),
            )

        }

        is PeliculasUIState.ObtenerExitoUsuario -> onPeliculasObtenidas()
        is PeliculasUIState.ActualizarExitoPelicula -> onPeliculasObtenidas()
        else -> {
            onPeliculasObtenidas()
        }
    }
}

@Composable
fun PantallaCargando(modifier: Modifier) {

    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(R.drawable.cargando),
        contentDescription = "Cargando"
    )
}

@Composable
fun PantallaError(modifier: Modifier) {
    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(R.drawable.error),
        contentDescription = "Error"
    )
}

@Composable
fun PantallaListarPeliculas(
    modifier: Modifier,
    listaPeliculas: List<Peliculas>,
    onVerPelicula: (Int) -> Unit,
    onBuscarPuntuacion: (String) -> Unit,
    puntuacionObtenida: PuntuacionPeliculas,
    onPuntuacionActualizada: (Double) -> Unit,

    ) {

    var despliegue by remember { mutableStateOf(false) }
    var peliculaElegida by remember { mutableStateOf(listaPeliculas.get(0)) }
    Column(modifier = modifier) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            items(listaPeliculas) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .padding(end = 10.dp, start = 10.dp)
                        .clickable {
                            peliculaElegida = it
                            despliegue = true
                            onBuscarPuntuacion(it.id)
                        }
                ) {
                    AsyncImage(
                        model = it.imagen,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }
        if (despliegue) {
            dialogo(
                pelicula = peliculaElegida,
                abrirDialogo = true,
                puntuacion = puntuacionObtenida,
                onVerPelicula = onVerPelicula,
                onPuntuacionCambiada = onPuntuacionActualizada
            )
        }


    }
}


@Composable
fun dialogo(
    pelicula: Peliculas,
    abrirDialogo: Boolean,
    puntuacion: PuntuacionPeliculas,
    onPuntuacionCambiada: (Double) -> Unit,
    onVerPelicula: (Int) -> Unit,
) {
    val maxPuntuacion: Int = 5


    if (abrirDialogo) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, end = 20.dp, start = 20.dp)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pelicula.titulo,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 35.sp),
                    modifier = Modifier
                        .padding(top = 10.dp),
                )
                AsyncImage(
                    model = pelicula.imagen,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(300.dp)
                        .width(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Text(
                    text = pelicula.descripcion,
                    modifier = Modifier
                        .padding(10.dp),
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.visualizaciones) + ": " + pelicula.visualizaciones,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Text(
                        text = stringResource(R.string.puntuacion) + ": " + puntuacion.puntuacion,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        onVerPelicula(pelicula.visualizaciones + 1)
                    }) {
                        Text(stringResource(R.string.ver))
                    }
                    Row {
                        for (i in 1..maxPuntuacion) {
                            val starType = when {
                                i <= puntuacion.puntuacion.toInt() -> Icons.Filled.Star
                                i - 0.5 <= puntuacion.puntuacion && puntuacion.puntuacion < i -> Icons.Filled.Star
                                else -> Icons.Outlined.Star
                            }

                            val starTint = when {
                                i <= puntuacion.puntuacion.toInt() -> Color.Yellow
                                i - 0.5 <= puntuacion.puntuacion && puntuacion.puntuacion < i -> Color.Yellow
                                else -> Color.Gray
                            }

                            Icon(
                                imageVector = starType,
                                contentDescription = "Estrella $i",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        onPuntuacionCambiada(i.toDouble())
                                    },
                                tint = starTint
                            )
                        }
                    }

                }
            }
        }
    }
}


