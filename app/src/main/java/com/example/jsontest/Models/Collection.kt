package com.example.jsontest.Models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Collection(
    @SerializedName("href")
    val href: String
) : Parcelable