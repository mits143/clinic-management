package com.clinic.management.model.appointmentslots


import com.google.gson.annotations.SerializedName

data class AppointmentSlotsData(
    @SerializedName("afternoon")
    val afternoon: ArrayList<Afternoon>,
    @SerializedName("evening")
    val evening: ArrayList<Evening>,
    @SerializedName("morning")
    val morning: ArrayList<Morning>
)