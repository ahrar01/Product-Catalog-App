package com.example.jsontest.ui.products

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.jsontest.data.models.Catalog
import com.example.jsontest.R
import com.example.jsontest.util.PriceFormatter
import kotlinx.android.synthetic.main.item_view.view.*

class MyAdapter(private val catalog: MutableList<Catalog>) :
    RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private lateinit var context: Context
    var TAG = "CART_ITEM_ADAPTER"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        context = parent.context
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false))
    }

    override fun getItemCount(): Int = catalog.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val catalogData = catalog[position]

        holder.bindItems(context, catalogData)

    }

    lateinit var mClickListener: ClickListener

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }

    fun getItem(position: Int): Any {
        return catalog.get(position)
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(p0: View?) {
            if (p0 != null) {
                mClickListener.onClick(adapterPosition, p0)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bindItems(context: Context, catalogData: Catalog) {
            itemView.item_cart_name_tv.text = catalogData.name

            itemView.item_cart_price_tv.text = catalogData.price.toDouble().let {
                PriceFormatter.getFormattedPrice(
                    it
                )
            }
            itemView.item_cart_metric_weight_tv.text = catalogData.weight + "Kg"

            Log.i(TAG, catalogData.images[0].src)

            Glide.with(context).load(catalogData.images[0].src)
                .placeholder(R.drawable.cart)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(
                    RequestOptions().override(600, 200)
                        .centerCrop()
                )
                .into(itemView.item_cart_iv)
        }
    }
}