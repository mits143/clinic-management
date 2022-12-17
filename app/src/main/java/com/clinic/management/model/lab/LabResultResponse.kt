package com.clinic.management.model.lab

data class LabResultResponse(
    val `data`: ArrayList<LabData>,
    val message: String? = null,
    val status: Boolean? = null,
    val ws: String? = null
) : java.io.Serializable