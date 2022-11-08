package com.clinic.management.model.medicine


import com.google.gson.annotations.SerializedName

data class MedicineData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("short_description")
    val shortDescription: String
)