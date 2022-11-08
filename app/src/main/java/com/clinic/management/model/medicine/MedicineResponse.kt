package com.clinic.management.model.medicine


import com.google.gson.annotations.SerializedName

data class MedicineResponse(
    @SerializedName("data")
    val `data`: ArrayList<MedicineData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("ws")
    val ws: String
)