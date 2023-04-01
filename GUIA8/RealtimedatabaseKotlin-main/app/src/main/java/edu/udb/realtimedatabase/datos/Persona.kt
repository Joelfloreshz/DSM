package edu.udb.realtimedatabase.datos

class Persona {
    fun key(key: String?) {
    }

    var dui: String? = null
    var nombre: String? = null
    var fechaNaci: String? = null
    var genero: String? = null
    var peso: String? = null
    var altura: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(dui: String?, nombre: String?, fechaNaci: String?, genero: String?, peso: String?, altura: String?) {
        this.dui = dui
        this.nombre = nombre
        this.fechaNaci = fechaNaci
        this.genero = genero
        this.peso = peso
        this.altura = altura
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "dui" to dui,
            "nombre" to nombre,
            "fechaNaci" to fechaNaci,
            "genero" to genero,
            "peso" to peso,
            "altura" to altura,
            "key" to key,
            "per" to per
        )
    }
}