package com.clinic.management.model.appointmments


import com.google.gson.annotations.SerializedName

data class CancelAppointmentData(
    @SerializedName("booking_information")
    val bookingInformation: ArrayList<BookingInformation>,
    @SerializedName("doc_avg_rating")
    val docAvgRating: String,
    @SerializedName("doc_image")
    val docImage: String,
    @SerializedName("doc_name")
    val docName: String,
    @SerializedName("doc_specialization")
    val docSpecialization: String,
    @SerializedName("doctor_id")
    val doctorId: String
)