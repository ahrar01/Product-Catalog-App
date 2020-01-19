package com.example.jsontest.Utils

class PriceFormatter {
    // equivalent to static scope
    companion object{
        val CURRENCY_SYMBOL = "₹"

        var CURRENCY_FORMAT = " %.2f"

        fun getCurrencySymbol():String{
            return CURRENCY_SYMBOL
        }

        fun getCurrencyFormat():String{
            return CURRENCY_FORMAT
        }

        fun getFormattedPrice(price: Double):String{
            return CURRENCY_SYMBOL + String.format(CURRENCY_FORMAT,price)
        }

        fun getDiscount(regularPrice:Int,salePrice:Int) :String{
            var percentage = (salePrice / regularPrice) * 100
            return "DISCOUNT %"+ percentage
        }
    }
}