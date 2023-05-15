package com.example.carsmotors

class TipoAutomovil(var id: Int, val nombre: String, val descripcion: String?) {

    override fun toString(): String {
        return nombre
    }

}