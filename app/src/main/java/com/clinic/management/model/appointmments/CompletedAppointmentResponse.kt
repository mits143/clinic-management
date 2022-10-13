package com.clinic.management.model.appointmments


import com.google.gson.annotations.SerializedName

data class CompletedAppointmentResponse(
    @SerializedName("data")
    val `data`: ArrayList<CompleteAppointmentData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("ws")
    val ws: String
)