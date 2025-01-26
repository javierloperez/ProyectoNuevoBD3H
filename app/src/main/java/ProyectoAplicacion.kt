import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevobd3h.ui.PeliculasViewModel
import com.example.proyectonuevobd3h.R

enum class Pantallas(@StringRes val titulo :Int){
    Inicio(titulo = R.string.inicio),
    InsertarPuntuacion(titulo = R.string.insertar),
    ActualizarPuntuacion(titulo = R.string.actualizar)
}

@Composable
fun ProyectoAplicacion(
    viewModel: PeliculasViewModel = viewModel(factory = PeliculasViewModel.Factory),
    navController: NavHostController = rememberNavController()
){

}