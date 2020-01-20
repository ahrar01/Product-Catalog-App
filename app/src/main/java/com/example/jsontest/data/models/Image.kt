package com.example.jsontest.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    @SerializedName("alt")
    val alt: String,
    @SerializedName("date_created")
    val dateCreated: String,
    @SerializedName("date_created_gmt")
    val dateCreatedGmt: String,
    @SerializedName("date_modified")
    val dateModified: String,
    @SerializedName("date_modified_gmt")
    val dateModifiedGmt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("src")
    val src: String
) : Parcelable