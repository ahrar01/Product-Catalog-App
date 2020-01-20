package com.example.jsontest.data.network

import com.example.jsontest.data.models.Catalog
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://demo1.digialtyic.in/"

interface ProductsApi {

    @GET("catalog")
    suspend fun getCatalog(): Response<List<Catalog>>

    companion object {
        operator fun invoke(): ProductsApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductsApi::class.java)
        }
    }
}