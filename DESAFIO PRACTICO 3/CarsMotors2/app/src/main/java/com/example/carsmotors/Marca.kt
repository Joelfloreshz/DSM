package com.example.carsmotors

class Marca(var id: Int, val nombre: String = "", val pais: String) {

    override fun toString(): String {
        return nombre
    }

}