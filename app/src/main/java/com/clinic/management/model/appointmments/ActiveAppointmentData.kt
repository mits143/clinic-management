package com.clinic.management.model.appointmments


import com.google.gson.annotations.SerializedName

data class ActiveAppointmentData(
    @SerializedName("appointment_date")
    val appointmentDate: String,
    @SerializedName("appointment_time")
    val appointmentTime: String,
    @SerializedName("doc_avg_rating")
    val docAvgRating: String,
    @SerializedName("doc_image")
    val docImage: String,
    @SerializedName("doc_name")
    val docName: String,
    @SerializedName("doc_specialization")
    val docSpecialization: String,
    @SerializedName("doctor_id")
    val doctorId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("position_list")
    val position_list : ArrayList<ActivePositionListItem>
)