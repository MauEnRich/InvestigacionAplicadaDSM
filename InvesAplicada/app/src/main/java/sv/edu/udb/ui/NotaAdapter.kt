package sv.edu.udb.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sv.edu.udb.R
import sv.edu.udb.data.Nota

class NotaAdapter(
    private val onEditClick: (Nota) -> Unit,
    private val onDeleteClick: (Nota) -> Unit,
    private val onCheckChange: (Nota, Boolean) -> Unit
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    private var listaNotas: List<Nota> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun getItemCount(): Int = listaNotas.size

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = listaNotas[position]
        holder.bind(nota, onEditClick, onDeleteClick, onCheckChange)
    }

    fun actualizarNotas(nuevasNotas: List<Nota>) {
        listaNotas = nuevasNotas
        notifyDataSetChanged()
    }

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titulo: TextView = itemView.findViewById(R.id.txtTitulo)
        private val contenido: TextView = itemView.findViewById(R.id.txtContenido)
        private val checkFinalizado: CheckBox = itemView.findViewById(R.id.checkFinalizado)
        private val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(
            nota: Nota,
            onEditClick: (Nota) -> Unit,
            onDeleteClick: (Nota) -> Unit,
            onCheckChange: (Nota, Boolean) -> Unit
        ) {
            titulo.text = nota.titulo
            contenido.text = nota.contenido

            itemView.setBackgroundColor(
                if (nota.finalizada) android.graphics.Color.parseColor("#D3D3D3")
                else android.graphics.Color.parseColor("#FFFFFF")
            )

            checkFinalizado.setOnCheckedChangeListener(null)
            checkFinalizado.isChecked = nota.finalizada
            checkFinalizado.setOnCheckedChangeListener { _, isChecked ->
                onCheckChange(nota, isChecked)
            }

            btnEditar.setOnClickListener {
                onEditClick(nota)
            }

            btnEliminar.setOnClickListener {
                onDeleteClick(nota)
            }
        }
    }
}
