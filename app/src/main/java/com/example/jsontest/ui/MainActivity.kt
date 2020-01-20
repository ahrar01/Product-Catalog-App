package com.example.jsontest.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jsontest.ui.products.IndividualProduct
import com.example.jsontest.ui.products.MyAdapter
import com.example.jsontest.R
import com.example.jsontest.data.models.Catalog
import com.example.jsontest.data.network.ProductsApi
import com.example.jsontest.data.repositories.ProductsRepository
import com.example.jsontest.ui.products.ProductsViewModel
import com.example.jsontest.ui.products.ProductsViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var factory: ProductsViewModelFactory
    private lateinit var viewModel: ProductsViewModel
    lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_progress_bar.visibility = View.VISIBLE

        val api = ProductsApi()
        val repository = ProductsRepository(api)

        factory = ProductsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ProductsViewModel::class.java)

        viewModel.getProducts()

        fetchCatalog()

        sortPrice.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.sort_bottom_sheet, null)

            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun fetchCatalog() {

        viewModel.products.observe(this, Observer { movies ->
            activity_items_rv.also {
                it.layoutManager = GridLayoutManager(this, 2)
                it.setHasFixedSize(true)
                adapter = MyAdapter(movies as MutableList<Catalog>)
                it.adapter = adapter
                activity_progress_bar.visibility = View.GONE

                adapter.setOnItemClickListener(object :
                    MyAdapter.ClickListener {
                    override fun onClick(pos: Int, aView: View) {
                        val cartItem: Catalog = adapter.getItem(pos) as Catalog;
                        if (cartItem == null)
                            return;
                        val intent = Intent(this@MainActivity, IndividualProduct::class.java)
                        intent.putExtra("product", cartItem)
                        startActivity(intent)
                    }

                })
            }
        })

    }

}
