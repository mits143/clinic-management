package com.clinic.management.model.lab

data class LabData(
    val booking_information: ArrayList<BookingInformation>,
    val pathology_address: String? = null,
    val pathology_avg_rating: Int? = null,
    val pathology_logo: String? = null,
    val pathology_name: String? = null
) : java.io.Serializable