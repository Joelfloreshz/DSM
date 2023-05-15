package com.example.carsmotors

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carsmotors.db.HelperDB

class LoginActivity : AppCompatActivity() {

    private lateinit var helperDB: HelperDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        helperDB = HelperDB(this)

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val usuario = helperDB.getUsuario(username, password)
            if (usuario != null) {
                val intent = when (usuario.tipo) {
                    "ADMIN" -> Intent(this, AdminActivity::class.java)
                    "CLIENT" -> Intent(this, ClientActivity::class.java)
                    else -> throw IllegalArgumentException("Tipo de usuario no válido")
                }
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_DESTINATION)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_DESTINATION && resultCode == RESULT_OK && data != null) {
            val usuario = data.getSerializableExtra("usuario") as? Usuario
            if (usuario != null) {
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_DESTINATION = 1001
    }
}
