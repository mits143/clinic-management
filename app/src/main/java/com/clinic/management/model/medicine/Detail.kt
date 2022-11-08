package com.clinic.management.model.medicine


import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
)