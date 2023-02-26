package com.clinic.management.model.home


import com.google.gson.annotations.SerializedName

data class SpecialCategory(
    @SerializedName("display_name") val displayName: String,
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: String,
    var isChecked: Boolean
)