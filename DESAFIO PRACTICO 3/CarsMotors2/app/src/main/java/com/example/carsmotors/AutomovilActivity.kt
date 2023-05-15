package com.example.carsmotors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

import com.example.carsmotors.db.HelperDB

import android.app.Dialog
import android.util.Log

class AutomovilActivity : AppCompatActivity() {

    private lateinit var db: HelperDB
    private lateinit var adapter: ArrayAdapter<Automovil>
    private lateinit var automovilesList: ListView
    private lateinit var automoviles: MutableList<Automovil>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automovil)

        db = HelperDB(this)

        automoviles = db.getAutomovil().toMutableList()

        automovilesList = findViewById(R.id.automoviles_list)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, automoviles)
        automovilesList.choiceMode = ListView.CHOICE_MODE_SINGLE
        automovilesList.adapter = adapter

        automovilesList.setOnItemClickListener { _, _, position, _ ->
            val automovil = adapter.getItem(position)
            mostrarDialogoEditar(automovil!!)
        }

        automovilesList.setOnItemLongClickListener { _, _, position, _ ->
            val automovil = adapter.getItem(position)
            db.deleteAutomovil(automovil!!)
            adapter.remove(automovil)
            automoviles.remove(automovil)
            true
        }

        val btnAgregar = findViewById<Button>(R.id.btn_agregar)
        btnAgregar.setOnClickListener {
            mostrarDialogoAgregar()
        }

        val btnEliminar = findViewById<Button>(R.id.btn_eliminar)
        btnEliminar.setOnClickListener {
            val position = automovilesList.checkedItemPosition
            if (position != ListView.INVALID_POSITION) {
                val automovil = adapter.getItem(position)
                db.deleteAutomovil(automovil!!)
                adapter.remove(automovil)
                automoviles.remove(automovil)
            }
        }
    }

    private fun agregarAutomovil(automovil: Automovil) {
        try {
            db.insertAutomovil(automovil)
            automoviles.add(automovil)
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Log.e("AutomovilActivity", "Error al agregar automóvil: ${e.message}")
            Toast.makeText(this, "Error al agregar automóvil: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editarAutomovil(automovil: Automovil) {
        try {
            db.updateAutomovil(automovil)
            val index = automoviles.indexOfFirst { it.id == automovil.id }
            automoviles[index] = automovil
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Log.e("AutomovilActivity", "Error al editar automóvil: ${e.message}")
            Toast.makeText(this, "Error al editar automóvil: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDialogoAgregar() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialogo_automovil)

        val btnAgregar = dialog.findViewById<Button>(R.id.btn_dialogo_agregar)
        val btnCancelar = dialog.findViewById<Button>(R.id.btn_dialogo_cancelar)

        btnAgregar.setOnClickListener {
            val modelo = dialog.findViewById<EditText>(R.id.et_dialogo_modelo).text.toString()
            val numeroVIN = dialog.findViewById<EditText>(R.id.et_dialogo_numero_vin).text.toString()
            val numeroChasis = dialog.findViewById<EditText>(R.id.et_dialogo_numero_chasis).text.toString()
            val numeroMotor = dialog.findViewById<EditText>(R.id.et_dialogo_numero_motor).text.toString()
            val numeroAsientos = dialog.findViewById<EditText>(R.id.et_dialogo_numero_asientos).text.toString().toInt()
            val anio = dialog.findViewById<EditText>(R.id.et_dialogo_anio).text.toString().toInt()
            val capacidadAsientos = dialog.findViewById<EditText>(R.id.et_dialogo_capacidad_asientos).text.toString().toInt()
            val precio = dialog.findViewById<EditText>(R.id.et_dialogo_precio).text.toString().toDouble()
            val uriImg = dialog.findViewById<EditText>(R.id.et_dialogo_uri_img).text.toString()
            val descripcion = dialog.findViewById<EditText>(R.id.et_dialogo_descripcion).text.toString()
            val marcaId = dialog.findViewById<EditText>(R.id.et_dialogo_marca_id).text.toString().toInt()
            val tipoAutomovilId = dialog.findViewById<EditText>(R.id.et_dialogo_tipo_automovil_id).text.toString().toInt()
            val colorId = dialog.findViewById<EditText>(R.id.et_dialogo_color_id).text.toString().toInt()

            val marca = Marca(marcaId, pais = "")
            val tipoAutomovil = TipoAutomovil(tipoAutomovilId, "", descripcion = null)
            val color = Color(colorId, "", codigoHexadecimal = "")


                val automovil = Automovil(
                    id = 1,
            modelo = modelo,
            numeroVIN = numeroVIN,
            numeroChasis = numeroChasis,
            numeroMotor = numeroMotor,
            numeroAsientos = numeroAsientos,
            anio = anio,
            capacidadAsientos = capacidadAsientos,
            precio = precio,
            uriImg = uriImg,
            descripcion = descripcion,
            marca = marca,
            tipoAutomovil = tipoAutomovil,
            color = color
            )

            agregarAutomovil(automovil)
            dialog.dismiss()
        }

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarDialogoEditar(automovil: Automovil) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialogo_automovil)

        val btnEditar = dialog.findViewById<Button>(R.id.btn_dialogo_agregar)
        val btnCancelar = dialog.findViewById<Button>(R.id.btn_dialogo_cancelar)

        val etModelo = dialog.findViewById<EditText>(R.id.et_dialogo_modelo)
        val etNumeroVIN = dialog.findViewById<EditText>(R.id.et_dialogo_numero_vin)
        val etNumeroChasis = dialog.findViewById<EditText>(R.id.et_dialogo_numero_chasis)
        val etNumeroMotor = dialog.findViewById<EditText>(R.id.et_dialogo_numero_motor)
        val etNumeroAsientos = dialog.findViewById<EditText>(R.id.et_dialogo_numero_asientos)
        val etAnio = dialog.findViewById<EditText>(R.id.et_dialogo_anio)
        val etCapacidadAsientos = dialog.findViewById<EditText>(R.id.et_dialogo_capacidad_asientos)
        val etPrecio = dialog.findViewById<EditText>(R.id.et_dialogo_precio)
        val etUriImg = dialog.findViewById<EditText>(R.id.et_dialogo_uri_img)
        val etDescripcion = dialog.findViewById<EditText>(R.id.et_dialogo_descripcion)
        val etMarcaId = dialog.findViewById<EditText>(R.id.et_dialogo_marca_id)
        val etTipoAutomovilId = dialog.findViewById<EditText>(R.id.et_dialogo_tipo_automovil_id)
        val etColorId = dialog.findViewById<EditText>(R.id.et_dialogo_color_id)

        etModelo.setText(automovil.modelo)
        etNumeroVIN.setText(automovil.numeroVIN)
        etNumeroChasis.setText(automovil.numeroChasis)
        etNumeroMotor.setText(automovil.numeroMotor)
        etNumeroAsientos.setText(automovil.numeroAsientos.toString())
        etAnio.setText(automovil.anio.toString())
        etCapacidadAsientos.setText(automovil.capacidadAsientos.toString())
        etPrecio.setText(automovil.precio.toString())
        etUriImg.setText(automovil.uriImg)
        etDescripcion.setText(automovil.descripcion)
        etMarcaId.setText(automovil.marca.id.toString())
        etTipoAutomovilId.setText(automovil.tipoAutomovil.id.toString())
        etColorId.setText(automovil.color.id.toString())

        btnEditar.setOnClickListener {
            val modelo = etModelo.text.toString()
            val numeroVIN = etNumeroVIN.text.toString()
            val numeroChasis = etNumeroChasis.text.toString()
            val numeroMotor = etNumeroMotor.text.toString()
            val numeroAsientos = etNumeroAsientos.text.toString().toInt()
            val anio = etAnio.text.toString().toInt()
            val capacidadAsientos = etCapacidadAsientos.text.toString().toInt()
            val precio = etPrecio.text.toString().toDouble()
            val uriImg = etUriImg.text.toString()
            val descripcion = etDescripcion.text.toString()
            val marcaId = etMarcaId.text.toString().toInt()
            val tipoAutomovilId = etTipoAutomovilId.text.toString().toInt()
            val colorId = etColorId.text.toString().toInt()

            val marca = Marca(marcaId, pais = "")
            val tipoAutomovil = TipoAutomovil(tipoAutomovilId, "", descripcion = null)
            val color = Color(colorId, "", codigoHexadecimal = "")

            val automovilEditado = Automovil(
                id = automovil.id,
                modelo = modelo,
                numeroVIN = numeroVIN,
                numeroChasis = numeroChasis,
                numeroMotor = numeroMotor,
                numeroAsientos = numeroAsientos,
                anio = anio,
                capacidadAsientos = capacidadAsientos,
                precio = precio,
                uriImg = uriImg,
                descripcion = descripcion,
                marca = marca,
                tipoAutomovil = tipoAutomovil,
                color = color
            )

            editarAutomovil(automovilEditado)
            dialog.dismiss()
        }

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}