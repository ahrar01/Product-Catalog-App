package com.example.jsontest.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jsontest.data.models.Catalog
import com.example.jsontest.data.repositories.ProductsRepository
import com.example.jsontest.util.Coroutines
import kotlinx.coroutines.Job

class ProductsViewModel(
    private val repository: ProductsRepository
) : ViewModel() {

    private lateinit var job: Job

    private val _catalog = MutableLiveData<List<Catalog>>()
    val products: LiveData<List<Catalog>>
        get() = _catalog

    fun getProducts() {
        job = Coroutines.ioThenMain(
            { repository.getProducts() },
            { _catalog.value = it }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) job.cancel()
    }
}
