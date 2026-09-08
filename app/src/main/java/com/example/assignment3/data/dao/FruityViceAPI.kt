package com.example.assignment3.data.dao

import android.telecom.Call
import com.example.assignment3.data.model.Fruit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface FruityViceAPI {
    @GET("/api/fruit/{name}")
    suspend fun getFruitByName(@Path("name") name: String): Fruit

    companion object {

        val BASE_URL = "https://fruityvice.com"

        fun create(): FruityViceAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(FruityViceAPI::class.java)
        }
    }
}