package sv.edu.udb.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas ORDER BY id DESC")
    fun obtenerNotas(): LiveData<List<Nota>>

    @Insert
    suspend fun insertarNota(nota: Nota)

    @Update
    suspend fun actualizarNota(nota: Nota)

    @Delete
    suspend fun eliminarNota(nota: Nota)
}
