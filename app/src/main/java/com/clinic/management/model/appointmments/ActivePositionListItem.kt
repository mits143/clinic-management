package com.clinic.management.model.appointmments


import com.google.gson.annotations.SerializedName

data class ActivePositionListItem(
    @SerializedName("status")
    val status: String,
    @SerializedName("username")
    val username: String
)