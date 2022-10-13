package com.clinic.management.model.home


import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("reviews_listing")
    val reviewsListing: ArrayList<ReviewsListing>,
    @SerializedName("special_category")
    val specialCategory: ArrayList<SpecialCategory>,
    @SerializedName("specialist_doctor")
    val specialistDoctor: ArrayList<SpecialistDoctor>,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("top_doctors")
    val topDoctors: ArrayList<SpecialistDoctor>,
    @SerializedName("ws")
    val ws: String
)