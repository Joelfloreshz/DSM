package com.example.carsmotors

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carsmotors.db.HelperDB

class RegisterActivity : AppCompatActivity() {

    private lateinit var helperDB: HelperDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        helperDB = HelperDB(this)

        val etNombres = findViewById<EditText>(R.id.et_nombres)
        val etApellidos = findViewById<EditText>(R.id.et_apellidos)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val rbAdmin = findViewById<RadioButton>(R.id.rb_admin)
        val rbClient = findViewById<RadioButton>(R.id.rb_client)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        btnRegister.setOnClickListener {
            val nombres = etNombres.text.toString()
            val apellidos = etApellidos.text.toString()
            val email = etEmail.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val tipo = if (rbAdmin.isChecked) "ADMIN" else "CLIENT"

            if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                val usuario = Usuario(0, nombres, apellidos, email, username, password, tipo)
                val id = helperDB.insertUsuario(usuario)
                if (id > 0) {
                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}