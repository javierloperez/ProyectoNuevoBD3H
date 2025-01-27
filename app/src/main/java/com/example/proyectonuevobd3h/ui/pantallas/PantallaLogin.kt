package com.example.proyectonuevobd3h.ui.pantallas

import android.graphics.Paint.Style
import android.media.metrics.PlaybackStateEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectonuevobd3h.R
import com.example.proyectonuevobd3h.modelo.Usuario
import com.example.proyectonuevobd3h.ui.PeliculasUIState

@Composable
fun PantallaLogin(
    appUIState: PeliculasUIState,
    onLoginPulsado: () -> Unit,
    onUsuariosObtenidos: () -> Unit,
    modifier: Modifier
) {

    var usuarios by remember { mutableStateOf(emptyList<Usuario>()) }

    when(appUIState){

        is PeliculasUIState.Cargando ->  PantallaCargando(modifier = Modifier.fillMaxWidth())
        is PeliculasUIState.Error ->  PantallaError(modifier = Modifier.fillMaxWidth())
        is PeliculasUIState.ObtenerExitoUsuario -> usuarios = appUIState.usuario
        
        else ->{

        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if(usuarios.isNotEmpty()) {
            Text(
                text = stringResource(R.string.usuario)+": "+usuarios[0].user,
                style = TextStyle(fontSize = 26.sp)
            )
            Spacer(modifier= Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.correo)+": "+ usuarios[0].correo,
                style = TextStyle(fontSize = 26.sp))
        }
        Spacer(modifier= Modifier.height(16.dp))

        Button(
            onClick = { onLoginPulsado() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)


        ) {
            Text(stringResource(R.string.login),
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .width(100.dp)
                    .height(35.dp)
            )
        }
    }
}

