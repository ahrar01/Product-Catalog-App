package com.example.jsontest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jsontest.Activity.IndividualProduct
import com.example.jsontest.Adapter.MyAdapter
import com.example.jsontest.Models.Catalog
import com.google.android.material.bottomsheet.BottomSheetDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_progress_bar.visibility = View.VISIBLE

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
        ProductsApi().getCatalog().enqueue(object : Callback<List<Catalog>> {
            override fun onFailure(call: Call<List<Catalog>>, t: Throwable) {
                activity_progress_bar.visibility = View.GONE

                Log.d("testError : ",t.message.toString())
                Toasty.error(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<List<Catalog>>, response: Response<List<Catalog>>) {
                val movies = response.body()

                movies?.let {
                    showCatalog(it)

                }

            }

        })
    }

    private fun showCatalog(movies: List<Catalog>) {

        adapter = MyAdapter(movies as MutableList<Catalog>)
        activity_items_rv.layoutManager = GridLayoutManager(this,2)
        activity_items_rv.adapter = adapter
        activity_progress_bar.visibility = View.GONE

        adapter.setOnItemClickListener(object :MyAdapter.ClickListener{
            override fun onClick(pos: Int, aView: View) {
                val cartItem: Catalog = adapter.getItem(pos) as Catalog;
                if (cartItem == null)
                    return;
                val intent = Intent(this@MainActivity, IndividualProduct::class.java)
                intent.putExtra("product", cartItem)
                startActivity(intent)            }

        })
    }
}
