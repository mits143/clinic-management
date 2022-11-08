package com.clinic.management.model.appointmments


import com.google.gson.annotations.SerializedName

data class CancelAppointmentResponse(
    @SerializedName("data")
    val `data`: ArrayList<CancelAppointmentData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("ws")
    val ws: String
)