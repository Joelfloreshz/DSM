package com.example.carsmotors

class Color(var id: Int, val nombre: String, val codigoHexadecimal: String) {

    var descripcion: String? = null

    override fun toString(): String {
        return nombre
    }
}