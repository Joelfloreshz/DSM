package com.example.carsmotors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.carsmotors.db.HelperDB

class ClientActivity : AppCompatActivity() {

    private lateinit var dbHelper: HelperDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        dbHelper = HelperDB(this)
        val automoviles = dbHelper.getAutomovil()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, automoviles.map { it.modelo })

        val listView = findViewById<ListView>(R.id.lista_automoviles)
        listView.adapter = adapter
    }
}