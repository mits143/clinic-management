package com.clinic.management.model.home


import com.google.gson.annotations.SerializedName

data class SpecialistDoctor(
    @SerializedName("avg_rating")
    val avgRating: String,
    @SerializedName("degree")
    val degree: String,
    @SerializedName("doc_name")
    val docName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("specialization")
    val specialization: String,
    @SerializedName("total_year_exp")
    val totalYearExp: String
)