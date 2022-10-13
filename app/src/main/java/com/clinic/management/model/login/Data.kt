package com.clinic.management.model.login


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: String,
//    @SerializedName("logo")
//    val logo: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("token")
    val token: String
)