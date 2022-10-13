package com.clinic.management.model.doctor


import com.google.gson.annotations.SerializedName

data class DoctorDetailData(
    @SerializedName("address")
    val address: String,
    @SerializedName("avg_rating")
    val avgRating: String,
    @SerializedName("degree")
    val degree: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("doc_name")
    val docName: String,
    @SerializedName("education_information")
    val educationInformation: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("specialization")
    val specialization: String,
    @SerializedName("total_year_exp")
    val totalYearExp: String
)