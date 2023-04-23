package com.example.guia10.viewmodels

import android.util.Log
import android.widget.Toast
import com.example.guia10.api.Conexion
import com.example.guia10.model.Alumno
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlumnoViewModel {

    lateinit var conexion: Conexion
    var call = conexion.getAPI()
    lateinit var data: List<Alumno>


    fun obtenerAlumnos() : List<Alumno>{
        call?.obtenerAlumnos()?.enqueue(object : Callback<List<Alumno>> {
            override fun onResponse(call: Call<List<Alumno>>, response: Response<List<Alumno>>) {
                if (response.isSuccessful) {
                    val alumnos = response.body()
                    if (alumnos != null) {
                        data = alumnos
                    }
                }
                else {
                    val error = response.errorBody()?.string()
                    Log.e("API", "Error al obtener los alumnos: $error")

                }
            }

            override fun onFailure(call: Call<List<Alumno>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return data
    }
}