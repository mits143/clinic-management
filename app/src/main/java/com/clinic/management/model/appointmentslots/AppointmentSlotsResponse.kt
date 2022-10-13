package com.clinic.management.model.appointmentslots


import com.google.gson.annotations.SerializedName

data class AppointmentSlotsResponse(
    @SerializedName("data")
    val `data`: AppointmentSlotsData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("ws")
    val ws: String
)