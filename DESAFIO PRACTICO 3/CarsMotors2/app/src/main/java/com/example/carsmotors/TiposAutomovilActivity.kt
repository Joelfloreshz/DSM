package com.example.carsmotors

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.carsmotors.db.HelperDB

class TiposAutomovilActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private lateinit var listaTiposAutomovil: ListView
    private lateinit var campoTexto: EditText
    private lateinit var btnNuevo: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnCancelar: Button
    private var tipoAutomovilSeleccionado: Int = -1
    private val tiposAutomovil: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipos_automovil)

        db = HelperDB(this).writableDatabase
        listaTiposAutomovil = findViewById(R.id.listaTiposAutomovil)
        campoTexto = findViewById(R.id.campoTexto)
        btnNuevo = findViewById(R.id.btnNuevo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)


        actualizarListaTiposAutomovil()

        listaTiposAutomovil.setOnItemClickListener { _, _, position, _ ->
            tipoAutomovilSeleccionado = position
            campoTexto.setText(tiposAutomovil[position])
            btnGuardar.isEnabled = true
            btnEliminar.isEnabled = true
        }

        btnNuevo.setOnClickListener {
            campoTexto.setText("")
            campoTexto.isEnabled = true
            btnGuardar.isEnabled = true
            btnEliminar.isEnabled = false
            tipoAutomovilSeleccionado = -1
        }

        btnGuardar.setOnClickListener {
            val tipoAutomovil = campoTexto.text.toString().trim()
            if (tipoAutomovil.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese la descripción del tipo de automóvil", Toast.LENGTH_SHORT).show()
            } else {
                if (tipoAutomovilSeleccionado == -1) {
                    insertarTipoAutomovil(tipoAutomovil)
                } else {
                    actualizarTipoAutomovil(tipoAutomovil)
                    tipoAutomovilSeleccionado = -1
                }
                campoTexto.setText("")
                campoTexto.isEnabled = false
                btnGuardar.isEnabled = false
                btnEliminar.isEnabled = false
                actualizarListaTiposAutomovil()
            }
        }

        btnEliminar.setOnClickListener {
            if (tipoAutomovilSeleccionado == -1) {
                Toast.makeText(this, "Por favor, seleccione un tipo de automóvil de la lista", Toast.LENGTH_SHORT).show()
            } else {
                val tipoAutomovil = tiposAutomovil[tipoAutomovilSeleccionado]
                eliminarTipoAutomovil(tipoAutomovil)
                campoTexto.setText("")
                campoTexto.isEnabled = false
                btnGuardar.isEnabled = false
                btnEliminar.isEnabled = false
                tipoAutomovilSeleccionado = -1
                actualizarListaTiposAutomovil()
            }
        }

    }

    private fun actualizarListaTiposAutomovil() {
        tiposAutomovil.clear()
        val cursor: Cursor = db.rawQuery("SELECT descripcion FROM tipo_automovil", null)
        while (cursor.moveToNext()) {
            tiposAutomovil.add(cursor.getString(0))
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tiposAutomovil)
        listaTiposAutomovil.adapter = adapter
    }

    private fun insertarTipoAutomovil(tipoAutomovil: String) {
        val valores = ContentValues()
        valores.put("descripcion", tipoAutomovil)
        db.insert("tipo_automovil", null, valores)
    }

    private fun actualizarTipoAutomovil(tipoAutomovil: String) {
        val valores = ContentValues()
        valores.put("descripcion", tipoAutomovil)
        val whereClause = "descripcion = ?"
        val whereArgs = arrayOf(tiposAutomovil[tipoAutomovilSeleccionado])
        db.update("tipo_automovil", valores, whereClause, whereArgs)
    }

    private fun eliminarTipoAutomovil(tipoAutomovil: String) {
        val whereClause = "descripcion = ?"
        val whereArgs = arrayOf(tipoAutomovil)
        db.delete("tipo_automovil", whereClause, whereArgs)
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}