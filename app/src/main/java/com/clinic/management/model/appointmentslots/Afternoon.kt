package com.clinic.management.model.appointmentslots


import com.google.gson.annotations.SerializedName

data class Afternoon(
    @SerializedName("day_name")
    val dayName: String,
    @SerializedName("limit_patient")
    val limitPatient: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("time")
    val time: String,
    var isChecked: Boolean
)