package sv.edu.udb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sv.edu.udb.data.NotaDao

class NotasViewModelFactory(private val dao: NotaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotasViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
