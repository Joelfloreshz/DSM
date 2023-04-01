package edu.udb.realtimedatabase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.udb.realtimedatabase.PersonasActivity.Companion.database
import edu.udb.realtimedatabase.datos.Persona

class AddPersonaActivity : AppCompatActivity() {
    private var edtDUI: EditText? = null
    private var edtNombre: EditText? = null
    private var edtFechaNaci: EditText? = null
    private var edtGenero: EditText? = null
    private var edtPeso: EditText? = null
    private var edtAltura: EditText? = null
    private var key = ""
    private var nombre = ""
    private var dui = ""
    private var accion = ""
    private lateinit var  database:DatabaseReference

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_persona)
        inicializar()
    }

    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.edtNombre)
        edtDUI = findViewById<EditText>(R.id.edtDUI)
        edtFechaNaci = findViewById<EditText>(R.id.edtFechaNaci)
        edtGenero = findViewById<EditText>(R.id.edtGenero)
        edtPeso = findViewById<EditText>(R.id.edtPeso)
        edtAltura = findViewById<EditText>(R.id.edtAltura)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtDUI = findViewById<EditText>(R.id.edtDUI)
        val edtFechaNaci = findViewById<EditText>(R.id.edtFechaNaci)
        val edtGenero = findViewById<EditText>(R.id.edtGenero)
        val edtPeso = findViewById<EditText>(R.id.edtPeso)
        val edtAltura = findViewById<EditText>(R.id.edtAltura)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtDUI.setText(intent.getStringExtra("dui").toString())
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            edtFechaNaci.setText(intent.getStringExtra("fechaNaci").toString())
        }
        if (datos != null) {
            edtGenero.setText(intent.getStringExtra("genero").toString())
        }
        if (datos != null) {
            edtPeso.setText(intent.getStringExtra("peso").toString())
        }
        if (datos != null) {
            edtAltura.setText(intent.getStringExtra("altura").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }

    }


    fun guardar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val dui: String = edtDUI?.text.toString()
        val fechaNaci: String = edtFechaNaci?.text.toString()
        val genero: String = edtGenero?.text.toString()
        val peso: String = edtPeso?.text.toString()
        val altura: String = edtAltura?.text.toString()

        database=FirebaseDatabase.getInstance().getReference("personas")

        // Se forma objeto persona
        val persona = Persona(dui, nombre, fechaNaci, genero, peso, altura)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(persona).addOnSuccessListener {
                Toast.makeText(this,"Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
            }
        } else  // Editar registro
        {
            val key = database.child("nombre").push().key
            if (key == null) {
                Toast.makeText(this,"Llave vacia", Toast.LENGTH_SHORT).show()
            }
            val personasValues = persona.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to personasValues
            )
            database.updateChildren(childUpdates)
            Toast.makeText(this,"Se actualizo con exito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }
}