package com.example.jsontest.ui.products

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.jsontest.data.models.Catalog
import com.example.jsontest.R
import com.example.jsontest.util.PriceFormatter
import kotlinx.android.synthetic.main.activity_individual_product.*

class IndividualProduct : AppCompatActivity() {

    var cartItem: Catalog? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_product)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        cartItem = intent.getParcelableExtra("product") as Catalog

        init()

    }

    fun init() {
        Glide.with(this).load(cartItem!!.images[0].src)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate().into(productimage)


        product_name.setText(cartItem!!.name)
        var regularPrice = PriceFormatter.getFormattedPrice(cartItem!!.regularPrice.toDouble())
        var salePrice = PriceFormatter.getFormattedPrice(cartItem!!.salePrice.toDouble())
        product_price.text = HtmlCompat.fromHtml("<strike>$regularPrice</strike> $salePrice ",HtmlCompat.FROM_HTML_MODE_LEGACY)
        var str: String = cartItem!!.description
        product_desc.text = getTagValue(str)

    }

    private fun getTagValue(str: String): CharSequence? {
        val str = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            HtmlCompat.fromHtml(str, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        return str
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
