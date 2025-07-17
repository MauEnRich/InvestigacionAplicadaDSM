package sv.edu.udb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas")
data class Nota(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var titulo: String,
    var contenido: String,
    var finalizada: Boolean = false
)
