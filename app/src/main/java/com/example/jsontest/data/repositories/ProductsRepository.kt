package com.example.jsontest.data.repositories

import com.example.jsontest.data.network.ProductsApi


class ProductsRepository(
    private val api: ProductsApi
) : SafeApiRequest() {

    suspend fun getProducts() = apiRequest { api.getCatalog() }

}