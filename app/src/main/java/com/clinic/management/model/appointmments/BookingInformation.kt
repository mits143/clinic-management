package com.clinic.management.model.appointmments


import com.google.gson.annotations.SerializedName

data class BookingInformation(
    @SerializedName("appointment_date")
    val appointmentDate: String,
    @SerializedName("appointment_time")
    val appointmentTime: String,
    @SerializedName("id")
    val id: String
)