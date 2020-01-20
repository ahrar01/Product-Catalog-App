package com.example.jsontest.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jsontest.R
import com.example.jsontest.data.models.Catalog
import com.example.jsontest.data.network.ProductsApi
import com.example.jsontest.data.repositories.ProductsRepository
import com.example.jsontest.ui.products.IndividualProduct
import com.example.jsontest.ui.products.MyAdapter
import com.example.jsontest.ui.products.ProductsViewModel
import com.example.jsontest.ui.products.ProductsViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.filter_bottom_sheet.view.*
import kotlinx.android.synthetic.main.sort_bottom_sheet.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var factory: ProductsViewModelFactory
    private lateinit var viewModel: ProductsViewModel
    lateinit var adapter: MyAdapter
    var lowToHigh: Boolean = false
    var highToLow: Boolean = false
    lateinit var filteredItems: List<Catalog>
    lateinit var availableItems: List<Catalog>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = ProductsApi()
        val repository = ProductsRepository(api)

        factory = ProductsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ProductsViewModel::class.java)

        viewModel.getProducts()

        fetchCatalog()

        //SORT PRICE
        sortPrice.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.sort_bottom_sheet, null)
            view.highToLowPrice.setOnClickListener {
                highToLow = true
                lowToHigh = false
                fetchCatalog()
                dialog.dismiss()
            }
            view.lowToHighPrice.setOnClickListener {
                highToLow = false
                lowToHigh = true
                fetchCatalog()
                dialog.dismiss()
            }
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }

        //FILTER CATEGORY
        filterCategory.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.filter_bottom_sheet, null)
            view.chipVegetable.setOnClickListener {
                filteredItems = filterCartItems(availableItems, "Vegetable")
                updateRecylerView(filteredItems)

                Toasty.success(this, "Vegetables", Toast.LENGTH_SHORT).show();
                dialog.dismiss()
            }
            view.chipHerbs.setOnClickListener {
                filteredItems = filterCartItems(availableItems, "Herbs")
                updateRecylerView(filteredItems)
                Toasty.success(this, "Herbs ", Toast.LENGTH_SHORT).show();
                dialog.dismiss()
            }
            view.chipFish.setOnClickListener {
                filteredItems = filterCartItems(availableItems, "fish")
                updateRecylerView(filteredItems)
                Toasty.success(this, "Fish ", Toast.LENGTH_SHORT).show();
                dialog.dismiss()
            }
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun updateRecylerView(filteredItems: List<Catalog>) {

        adapter = MyAdapter(filteredItems as MutableList<Catalog>)
        activity_items_rv.adapter = adapter
        adapter.notifyDataSetChanged()

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

    fun filterCartItems(cartList: List<Catalog>, searchQuery: String): List<Catalog> {


        val filteredValues = ArrayList<Catalog>(cartList)
        for (cartItem in cartList) {
            val searchText = "${cartItem.name}  ${cartItem.categories}  ${cartItem.categories}"
            if (!(searchText.toLowerCase()).contains(searchQuery.toLowerCase()))
                filteredValues.remove(cartItem)
        }

        return filteredValues

    }

    private fun fetchCatalog() {
        activity_progress_bar.visibility = View.VISIBLE

        viewModel.products.observe(this, Observer { products ->
            availableItems = products
            activity_items_rv.also {
                it.layoutManager = GridLayoutManager(this, 2)
                it.setHasFixedSize(true)

                if (highToLow == true) {
                    adapter =
                        MyAdapter(products.sortedByDescending { products -> products.price } as MutableList<Catalog>)
                } else if (lowToHigh == true) {
                    adapter =
                        MyAdapter(products.sortedBy { products -> products.price } as MutableList<Catalog>)
                } else {
                    adapter = MyAdapter(products as MutableList<Catalog>)
                }
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
