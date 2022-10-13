package com.clinic.management.model.doctor


import com.google.gson.annotations.SerializedName

data class DoctorDetailResponse(
    @SerializedName("data")
    val `data`: ArrayList<DoctorDetailData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("ws")
    val ws: String
)