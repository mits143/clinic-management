package com.clinic.management.model.category


import com.clinic.management.model.home.SpecialCategory
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data") val `data`: ArrayList<SpecialCategory>,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean,
    @SerializedName("ws") val ws: String
)