package com.clinic.management.model.doctorResult

data class Data(
    val appointment_date: String,
    val appointment_time: String,
    val doc_avg_rating: String,
    val doc_image: String,
    val doc_name: String,
    val doc_prescription_note: String,
    val doc_specialization: String,
    val id: String,
    val pathology: Pathology,
    val pathology_lab_result: ArrayList<PathologyLabResult>,
    val pathology_note: String,
    val prescription_medicine: ArrayList<PrescriptionMedicine>,
    val radiology: Radiology,
    val radiology_lab_result: ArrayList<RadiologyLabResult>,
    val radiology_note: String,
    val is_review: String
)