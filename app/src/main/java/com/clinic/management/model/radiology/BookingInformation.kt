package com.clinic.management.model.radiology

data class BookingInformation(
    val appointment_date: String? = null,
    val doc_specialization: String? = null,
    val id: String? = null,
    val radiology_lab_result: ArrayList<RadiologyLabResult>,
    val radiology_note: String? = null,
    var radiology_name: String? = null,
    var radiology_logo: String? = null,
    var radiology_rating: String? = null
) : java.io.Serializable