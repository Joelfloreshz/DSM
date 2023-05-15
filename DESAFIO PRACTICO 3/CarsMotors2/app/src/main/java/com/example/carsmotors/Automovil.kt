package com.example.carsmotors



data class Automovil(
    val id: Int,
    var modelo: String,
    var numeroVIN: String,
    var numeroChasis: String,
    var numeroMotor: String,
    var numeroAsientos: Int,
    var anio: Int,
    var capacidadAsientos: Int,
    var precio: Double,
    var uriImg: String,
    var descripcion: String,
    val marca: Marca,
    val tipoAutomovil: TipoAutomovil,
    val color: Color
)