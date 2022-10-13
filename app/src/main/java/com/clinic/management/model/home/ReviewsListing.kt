package com.clinic.management.model.home


import com.google.gson.annotations.SerializedName

data class ReviewsListing(
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("user_id")
    val userId: String
)