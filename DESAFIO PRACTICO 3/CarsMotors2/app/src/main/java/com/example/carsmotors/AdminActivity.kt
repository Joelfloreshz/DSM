package com.example.carsmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
    }

    fun irAMarcas(view: View) {
        val intent = Intent(this, MarcasActivity::class.java)
        startActivity(intent)
    }

    fun irAColores(view: View) {
        val intent = Intent(this, coloresActivity::class.java)
        startActivity(intent)
    }

    fun irATipos(view: View) {
        val intent = Intent(this, TiposAutomovilActivity::class.java)
        startActivity(intent)
    }

    fun irAAutomovil(view: View) {
        val intent = Intent(this, AutomovilActivity::class.java)
        startActivity(intent)
    }
    fun irAUsuario(view: View) {
        val intent = Intent(this, UsuariosActivity::class.java)
        startActivity(intent)
    }
}