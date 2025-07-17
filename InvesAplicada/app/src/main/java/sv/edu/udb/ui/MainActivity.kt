package sv.edu.udb.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.udb.R
import sv.edu.udb.data.NotasDatabase
import sv.edu.udb.viewmodel.NotasViewModel
import sv.edu.udb.viewmodel.NotasViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerNotas: RecyclerView
    private lateinit var notasAdapter: NotaAdapter
    private lateinit var viewModel: NotasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCrearNota: Button = findViewById(R.id.btnCrearNota)
        btnCrearNota.setOnClickListener {
            val intent = Intent(this, CrearEditarNotaActivity::class.java)  // Aquí usamos la actividad unificada
            startActivity(intent)
        }

        recyclerNotas = findViewById(R.id.recyclerNotas)
        recyclerNotas.layoutManager = LinearLayoutManager(this)

        val database = NotasDatabase.getDatabase(this)
        val notaDao = database.notaDao()

        val factory = NotasViewModelFactory(notaDao)
        viewModel = ViewModelProvider(this, factory)[NotasViewModel::class.java]

        notasAdapter = NotaAdapter(
            onEditClick = { notaSeleccionada ->
                val intent = Intent(this, CrearEditarNotaActivity::class.java).apply {  // Usamos la misma aquí
                    putExtra("nota_id", notaSeleccionada.id)
                    putExtra("nota_titulo", notaSeleccionada.titulo)
                    putExtra("nota_contenido", notaSeleccionada.contenido)
                }
                startActivity(intent)
            },
            onDeleteClick = { nota ->
                viewModel.eliminar(nota)
            },
            onCheckChange = { nota, isChecked ->
                val notaActualizada = nota.copy(finalizada = isChecked)
                viewModel.actualizar(notaActualizada)
            }
        )

        recyclerNotas.adapter = notasAdapter


        viewModel.notas.observe(this) { notas ->
            notasAdapter.actualizarNotas(notas)
        }
    }
}
