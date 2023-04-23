package com.example.guia10.api

import com.example.guia10.model.Alumno
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("alumno.php")
    fun obtenerAlumnos(): Call<List<Alumno>>

    @GET("alumno.php/{id}")
    fun obtenerAlumnoPorId(@Path("id") id: Int): Call<Alumno>

    @POST("alumnos")
    fun CrearAlumno(@Body alumno: Alumno):Call<Alumno>

    @PUT("alumnos/{id}")
    fun actulizarAlumno(@Path("id") id: Int, @Body alumno: Alumno): Call<Alumno>

    @DELETE("alumnos/{id}")
    fun eliminarAlumno(@Path("id") id: Int): Call<Void>
}