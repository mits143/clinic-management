package com.clinic.management.model.radiology

data class RadiologyData(
    val booking_information: ArrayList<BookingInformation>,
    val radiology_address: String? = null,
    val radiology_avg_rating: Int? = null,
    val radiology_logo: String? = null,
    val radiology_name: String? = null
):java.io.Serializable