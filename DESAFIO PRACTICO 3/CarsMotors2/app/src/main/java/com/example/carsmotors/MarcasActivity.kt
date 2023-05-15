package com.example.carsmotors

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.carsmotors.db.HelperDB

class MarcasActivity : AppCompatActivity() {

    private lateinit var campoTexto: EditText
    private lateinit var listaMarcas: ListView
    private lateinit var btnNuevo: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnCancelar: Button
    private lateinit var db: SQLiteDatabase
    private lateinit var marcas: ArrayList<String>
    private var marcaSeleccionada: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marcas)

        campoTexto = findViewById(R.id.campoTexto)
        listaMarcas = findViewById(R.id.listaMarcas)
        btnNuevo = findViewById(R.id.btnNuevo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)


        marcas = ArrayList()
        db = HelperDB(this).writableDatabase
        cargarMarcasDesdeBD()

        listaMarcas.setOnItemClickListener { _, _, position, _ ->
            val marca = marcas[position]
            campoTexto.setText(marca)
            campoTexto.isEnabled = true
            btnGuardar.isEnabled = true
            btnEliminar.isEnabled = true
            marcaSeleccionada = position
        }

        btnNuevo.setOnClickListener {
            campoTexto.setText("")
            campoTexto.isEnabled = true
            btnGuardar.isEnabled = true
            btnEliminar.isEnabled = false
            marcaSeleccionada = -1
        }

        btnGuardar.setOnClickListener {
            val marca = campoTexto.text.toString()
            if (marca.isNotEmpty()) {
                if (marcaSeleccionada == -1) {
                    agregarMarca(marca)
                } else {
                    actualizarMarca(marcas[marcaSeleccionada], marca)
                }
                campoTexto.setText("")
                campoTexto.isEnabled = false
                btnGuardar.isEnabled = false
                btnEliminar.isEnabled = false
                marcaSeleccionada = -1
                cargarMarcasDesdeBD()
            } else {
                Toast.makeText(this, "Ingrese una marca antes de guardar", Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminar.setOnClickListener {
            if (marcaSeleccionada != -1) {
                val marca = marcas[marcaSeleccionada]
                eliminarMarca(marca)
                campoTexto.setText("")
                campoTexto.isEnabled = false
                btnGuardar.isEnabled = false
                btnEliminar.isEnabled = false
                marcaSeleccionada = -1
                cargarMarcasDesdeBD()
            } else {
                Toast.makeText(this, "Por favor, seleccione una marca para eliminar", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun cargarMarcasDesdeBD() {
        marcas.clear()
        val cursor = db.rawQuery("SELECT nombre FROM marcas", null)
        while (cursor.moveToNext()) {
            marcas.add(cursor.getString(0))
        }
        cursor.close()
        actualizarListaMarcas()
    }

    private fun actualizarListaMarcas() {
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, marcas)
        listaMarcas.adapter = adaptador
    }

    private fun agregarMarca(marca: String) {
        val values = ContentValues()
        values.put("nombre", marca)
        db.insert("marcas", null, values)
        Toast.makeText(this, "Se agregó la marca $marca", Toast.LENGTH_SHORT).show()
    }

    private fun actualizarMarca(marcaAnterior: String, marcaNueva: String) {
        val values = ContentValues()
        values.put("nombre", marcaNueva)
        val whereClause = "nombre = ?"
        val whereArgs = arrayOf(marcaAnterior)
        db.update("marcas", values, whereClause, whereArgs)
        Toast.makeText(this, "Se actualizó la marca $marcaAnterior a $marcaNueva", Toast.LENGTH_SHORT).show()
    }

    private fun eliminarMarca(marca: String) {
        val whereClause = "nombre = ?"
        val whereArgs = arrayOf(marca)
        val resultado = db.delete("marcas", whereClause, whereArgs)
        Toast.makeText(this, "Se eliminaron $resultado registros de la tabla marcas", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}