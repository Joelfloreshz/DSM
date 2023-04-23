package com.example.guia10.api

import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Conexion {

    constructor(){}
    private var auth_username = "admin"
    private var auth_password = "admin123"

    private val client = OkHttpClient.Builder()
        .addInterceptor{ chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", Credentials.basic(auth_username, auth_password))
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://168.243.3.36/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api = retrofit.create(ApiService::class.java)

    fun getAPI(): ApiService? {
        return api
    }
}