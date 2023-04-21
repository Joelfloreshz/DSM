package com.example.retrofitapp

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("{raza}/images")
    fun getDogsByBreed(@Path("raza") raza: String?): Call<DogsResponse?>?
}