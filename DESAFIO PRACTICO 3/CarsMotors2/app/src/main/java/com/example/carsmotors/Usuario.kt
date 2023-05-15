package com.example.carsmotors

import java.io.Serializable

data class Usuario(
    val id: Int,
    val nombres: String,
    val apellidos: String,
    val correoElectronico: String,
    val usuario: String,
    val contrasena: String,
    val tipo: String
): Serializable {
}