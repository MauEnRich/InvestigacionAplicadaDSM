package sv.edu.udb.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.edu.udb.data.Nota
import sv.edu.udb.data.NotaDao

class NotasViewModel(private val dao: NotaDao) : ViewModel() {

    val notas = dao.obtenerNotas()

    fun insertar(nota: Nota) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertarNota(nota)
        }
    }

    fun actualizar(nota: Nota) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.actualizarNota(nota)
        }
    }

    fun eliminar(nota: Nota) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.eliminarNota(nota)
        }
    }
}
