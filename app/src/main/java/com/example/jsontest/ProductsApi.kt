package com.example.jsontest

import com.example.jsontest.Models.Catalog
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://demo1.digialtyic.in/"

interface ProductsApi {

    @GET("catalog")
    fun getCatalog() : Call<List<Catalog>>

    companion object {
        operator fun invoke() : ProductsApi{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductsApi::class.java)
        }
    }
}