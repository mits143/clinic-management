package com.clinic.management.model.appointmments


import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    @SerializedName("data")
    val `data`: ArrayList<AppointmentData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("ws")
    val ws: String
)