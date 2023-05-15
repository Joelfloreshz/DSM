package com.example.carsmotors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter(
    private val usuarios: List<Usuario>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(usuario: Usuario)
        fun onItemLongClick(usuario: Usuario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.bind(usuario)
        holder.itemView.setOnClickListener { listener.onItemClick(usuario) }
        holder.itemView.setOnLongClickListener {
            listener.onItemLongClick(usuario)
            true
        }
    }

    override fun getItemCount() = usuarios.size

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textNombreCompleto: TextView = itemView.findViewById(R.id.textNombreCompleto)
        private val textCorreoElectronico: TextView = itemView.findViewById(R.id.textCorreoElectronico)

        fun bind(usuario: Usuario) {
            textNombreCompleto.text = "${usuario.nombres} ${usuario.apellidos}"
            textCorreoElectronico.text = usuario.correoElectronico
        }
    }
}