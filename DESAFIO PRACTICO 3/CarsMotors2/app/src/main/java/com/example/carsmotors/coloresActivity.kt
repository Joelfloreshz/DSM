package com.example.carsmotors

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.carsmotors.db.HelperDB

class coloresActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private lateinit var listaColores: ListView
    private lateinit var campoTexto: EditText
    private lateinit var btnNuevo: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnCancelar: Button
    private var colorSeleccionado: Int = -1
    private val colores: MutableList<String> = mutableListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colores)
        db = HelperDB(this).writableDatabase
        listaColores = findViewById(R.id.listaColores)
        campoTexto = findViewById(R.id.campoTexto)
        btnNuevo = findViewById(R.id.btnNuevo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)

        actualizarListaColores()
        listaColores.setOnItemClickListener { _, _, position, _ ->
            colorSeleccionado = position
            campoTexto.setText(listaColores.getItemAtPosition(position).toString())
            btnGuardar.isEnabled = true
            btnEliminar.isEnabled = true
        }
        btnNuevo.setOnClickListener {
            campoTexto.setText("")
            campoTexto.isEnabled = true
            btnGuardar.isEnabled = true
            btnEliminar.isEnabled = false
            colorSeleccionado = -1
        }
        btnGuardar.setOnClickListener {
            val color = campoTexto.text.toString().trim()
            if (color.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese la descripción del color", Toast.LENGTH_SHORT).show()
            } else {
                if (colorSeleccionado == -1) {
                    insertarColor(color)
                } else {
                    actualizarColor(color)
                    colorSeleccionado = -1
                }
                campoTexto.setText("")
                campoTexto.isEnabled = false
                btnGuardar.isEnabled = false
                btnEliminar.isEnabled = false
                actualizarListaColores()
            }
        }
        btnEliminar.setOnClickListener {
            val color = listaColores.getItemAtPosition(colorSeleccionado).toString()
            eliminarColor(color)
            campoTexto.setText("")
            campoTexto.isEnabled = false
            btnGuardar.isEnabled = false
            btnEliminar.isEnabled = false
            colorSeleccionado = -1
            actualizarListaColores()
        }

    }

    private fun actualizarListaColores() {
        colores.clear()
        val cursor: Cursor = db.rawQuery("SELECT descripcion FROM colores", null)
        while (cursor.moveToNext()) {
            colores.add(cursor.getString(0))
        }
        cursor.close()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, colores)
        listaColores.adapter = adapter
    }

    private fun insertarColor(color: String) {
        val valores = ContentValues()
        valores.put("descripcion", color)
        db.insert("colores", null, valores)
    }

    private fun actualizarColor(color: String) {
        val valores = ContentValues()
        valores.put("descripcion", color)
        val whereClause = "descripcion = ?"
        val whereArgs = arrayOf(colores[colorSeleccionado])
        db.update("colores", valores, whereClause, whereArgs)
    }

    private fun eliminarColor(color: String) {
        val whereClause = "descripcion = ?"
        val whereArgs = arrayOf(color)
        db.delete("colores", whereClause, whereArgs)
    }
}