package com.istea.worldcup.pages.detalle


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.worldcup.domain.Repository
import com.istea.worldcup.navigation.Router
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetalleViewModel(
    val repositorio: Repository,
    val router: Router,
    val groupID: String
) : ViewModel() {

    var uiState by mutableStateOf<DetalleState>(DetalleState.Vacio)

    fun ejecutar(intencion: DetalleIntencion) {
        when(intencion){
            is DetalleIntencion.IrParaAtras -> irParaAtras()
            is DetalleIntencion.CargarContenido -> cargarContenido()
        }
    }

    private fun irParaAtras(){
        router.back()
    }

    private fun cargarContenido(){
        uiState = DetalleState.Cargando
        viewModelScope.launch {
            try {
                repositorio.getGroup(groupID)?.let {
                    uiState = DetalleState.Resultado(grupo = it)
                }
            } catch (e: Exception) {
                uiState = DetalleState.Error(mensaje = "Error al cargar el contenido")
            }
        }
    }
}


class DetalleViewModelFactory(
    private val repositorio: Repository,
    private val router: Router,
    private val groupID: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleViewModel::class.java)) {
            return DetalleViewModel(repositorio,router,groupID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}