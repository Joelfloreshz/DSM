package com.example.carsmotors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotors.db.HelperDB

class UsuariosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var formulario: CardView
    private lateinit var editNombres: EditText
    private lateinit var editApellidos: EditText
    private lateinit var editCorreoElectronico: EditText
    private lateinit var editUsuario: EditText
    private lateinit var editContrasena: EditText
    private lateinit var editTipo: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    private lateinit var db: HelperDB
    private lateinit var adapter: UsuarioAdapter
    private var usuarios: MutableList<Usuario> = mutableListOf()
    private var usuarioSeleccionado: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)

        recyclerView = findViewById(R.id.recyclerView)
        formulario = findViewById(R.id.formulario)
        editNombres = findViewById(R.id.editNombres)
        editApellidos = findViewById(R.id.editApellidos)
        editCorreoElectronico = findViewById(R.id.editCorreoElectronico)
        editUsuario = findViewById(R.id.editUsuario)
        editContrasena = findViewById(R.id.editContrasena)
        editTipo = findViewById(R.id.editTipo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)

        db = HelperDB(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UsuarioAdapter(usuarios, object : UsuarioAdapter.OnItemClickListener {
            override fun onItemClick(usuario: Usuario) {
                mostrarFormulario(usuario)
            }

            override fun onItemLongClick(usuario: Usuario) {
                eliminarUsuario(usuario)
            }
        })
        recyclerView.adapter = adapter

        btnGuardar.setOnClickListener {
            if (usuarioSeleccionado == null) {
                val nuevoUsuario = Usuario(
                    0,
                    editNombres.text.toString(),
                    editApellidos.text.toString(),
                    editCorreoElectronico.text.toString(),
                    editUsuario.text.toString(),
                    editContrasena.text.toString(),
                    editTipo.text.toString()
                )
                insertarUsuario(nuevoUsuario)
            } else {
                val usuarioEditado = Usuario(
                    usuarioSeleccionado!!.id,
                    editNombres.text.toString(),
                    editApellidos.text.toString(),
                    editCorreoElectronico.text.toString(),
                    editUsuario.text.toString(),
                    editContrasena.text.toString(),
                    editTipo.text.toString()
                )
                actualizarUsuario(usuarioEditado)
            }
        }

        btnCancelar.setOnClickListener {
            ocultarFormulario()
        }

        cargarUsuarios("")
    }

    private fun cargarUsuarios(query: String) {
        usuarios.clear()
        usuarios.addAll(db.buscarUsuarios(query))
        adapter.notifyDataSetChanged()
    }

    private fun insertarUsuario(usuario: Usuario) {
        db.insertUsuario(usuario)
        cargarUsuarios("")
        ocultarFormulario()
    }

    private fun actualizarUsuario(usuario: Usuario) {
        db.updateUsuario(usuario)
        cargarUsuarios("")
        ocultarFormulario()
    }

    private fun eliminarUsuario(usuario: Usuario) {
        db.deleteUsuario(usuario.id)
        cargarUsuarios("")
    }

    private fun mostrarFormulario(usuario: Usuario?) {
        usuarioSeleccionado = usuario
        if (usuario == null) {
            editNombres.setText("")
            editApellidos.setText("")
            editCorreoElectronico.setText("")
            editUsuario.setText("")
            editContrasena.setText("")
            editTipo.setText("")
        } else {
            editNombres.setText(usuario.nombres)
            editApellidos.setText(usuario.apellidos)
            editCorreoElectronico.setText(usuario.correoElectronico)
            editUsuario.setText(usuario.usuario)
            editContrasena.setText(usuario.contrasena)
            editTipo.setText(usuario.tipo)
        }
        formulario.visibility = View.VISIBLE
    }

    private fun ocultarFormulario() {
        usuarioSeleccionado = null
        formulario.visibility = View.GONE
    }
}