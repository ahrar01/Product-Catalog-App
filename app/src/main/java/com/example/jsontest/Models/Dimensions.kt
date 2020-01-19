package com.example.jsontest.Models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dimensions(
    @SerializedName("height")
    val height: String,
    @SerializedName("length")
    val length: String,
    @SerializedName("width")
    val width: String
) : Parcelable