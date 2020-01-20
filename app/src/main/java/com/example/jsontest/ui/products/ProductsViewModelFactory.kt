package com.example.jsontest.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jsontest.data.repositories.ProductsRepository

@Suppress("UNCHECKED_CAST")
class ProductsViewModelFactory(
    private val repository: ProductsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductsViewModel(repository) as T
    }

}