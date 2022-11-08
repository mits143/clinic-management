package com.clinic.management.model.medicine


import com.google.gson.annotations.SerializedName

data class MedicineDetailResponse(
    @SerializedName("data")
    val `data`: MedicineDetailData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("ws")
    val ws: String
)