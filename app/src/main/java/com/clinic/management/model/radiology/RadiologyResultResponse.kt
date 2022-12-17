package com.clinic.management.model.radiology

data class RadiologyResultResponse(
    val `data`: ArrayList<RadiologyData>,
    val message: String? = null,
    val status: Boolean? = false,
    val ws: String? = null
) : java.io.Serializable