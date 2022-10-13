package com.clinic.management.model.doctor


import com.clinic.management.model.home.SpecialistDoctor
import com.google.gson.annotations.SerializedName

data class DoctorListingResponse(
    @SerializedName("data")
    val `data`: ArrayList<SpecialistDoctor>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("ws")
    val ws: String
)