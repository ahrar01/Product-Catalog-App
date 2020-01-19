package com.example.jsontest.Models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(
    @SerializedName("collection")
    val collection: List<Collection>,
    @SerializedName("self")
    val self: List<Self>
) : Parcelable