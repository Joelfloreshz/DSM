package com.example.appcalculadoramvc

import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CalculatorController(private val view: MainActivity) {
    private val model = CalculatorModel()
    init {
        // Asociar eventos de clic a los botones
        view.findViewById<Button>(R.id.btn_add).setOnClickListener {
            performOperation("add") }
        view.findViewById<Button>(R.id.btn_subtract).setOnClickListener {
            performOperation("subtract") }
        view.findViewById<Button>(R.id.btn_multiply).setOnClickListener {
            performOperation("multiply") }
        view.findViewById<Button>(R.id.btn_divide).setOnClickListener {
            performOperation("divide") }
        view.findViewById<Button>(R.id.btn_raiz).setOnClickListener {
            performOperation("raiz")
        }
    }

    private fun performOperation(operation: String) {
        // Obtener los números ingresados por el usuario
        val num1 = view.findViewById<EditText>(R.id.num1).text.toString()
        var num2 = view.findViewById<EditText>(R.id.num2).text.toString()

        // Realizar la operación correspondiente
        val result = when (operation) {
            "add" -> model.add(num1.toDouble(), num2.toDouble())
            "subtract" -> model.subtract(num1.toDouble(), num2.toDouble())
            "multiply" -> model.multiply(num1.toDouble(), num2.toDouble())
            "divide" -> model.divide(num1.toDouble(), num2.toDouble())
            "raiz" -> model.raiz(num1.toDouble())
            else -> throw IllegalArgumentException("Operación no válida")
        }
        // Actualizar la vista con el resultado
        view.findViewById<TextView>(R.id.result).text = "Resultado: $result"
    }
}