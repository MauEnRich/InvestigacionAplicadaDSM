package sv.edu.udb.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import sv.edu.udb.R
import sv.edu.udb.data.Nota
import sv.edu.udb.data.NotasDatabase
import sv.edu.udb.viewmodel.NotasViewModel
import sv.edu.udb.viewmodel.NotasViewModelFactory

class CrearEditarNotaActivity : AppCompatActivity() {

    private lateinit var tvTituloEncabezado: TextView
    private lateinit var etTitulo: EditText
    private lateinit var etContenido: EditText
    private lateinit var btnGuardar: Button
    private lateinit var viewModel: NotasViewModel
    private lateinit var btnRegresar: Button


    private var notaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_nota)

        tvTituloEncabezado = findViewById(R.id.tvTituloEncabezado)
        etTitulo = findViewById(R.id.etTitulo)
        etContenido = findViewById(R.id.etContenido)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnRegresar = findViewById(R.id.btnRegresar)

        btnRegresar.setOnClickListener {
            finish()
        }
        val database = NotasDatabase.getDatabase(this)
        val notaDao = database.notaDao()
        val factory = NotasViewModelFactory(notaDao)
        viewModel = ViewModelProvider(this, factory)[NotasViewModel::class.java]


        notaId = intent.getIntExtra("nota_id", -1)
        if (notaId != -1) {

            tvTituloEncabezado.text = "Edita esta tarea"
            etTitulo.setText(intent.getStringExtra("nota_titulo"))
            etContenido.setText(intent.getStringExtra("nota_contenido"))
        } else {

            tvTituloEncabezado.text = "Agrega una tarea"
        }

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val contenido = etContenido.text.toString().trim()

            if (titulo.isEmpty()) {
                Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (notaId != -1) {

                    val notaActualizada = Nota(id = notaId, titulo = titulo, contenido = contenido)
                    viewModel.actualizar(notaActualizada)
                    runOnUiThread {
                        Toast.makeText(this@CrearEditarNotaActivity, "Tarea actualizada", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {

                    val nuevaNota = Nota(titulo = titulo, contenido = contenido)
                    viewModel.insertar(nuevaNota)
                    runOnUiThread {
                        Toast.makeText(this@CrearEditarNotaActivity, "Tarea guardada", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}
