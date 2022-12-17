package com.clinic.management.model.lab

data class BookingInformation(
    val appointment_date: String? = null,
    val doc_specialization: String? = null,
    val id: String? = null,
    val pathology_lab_result: ArrayList<PathologyLabResult>,
    val pathology_note: String? = null,
    var lab_name: String? = null,
    var lab_logo: String? = null,
    var lab_rating: String? = null
) : java.io.Serializable