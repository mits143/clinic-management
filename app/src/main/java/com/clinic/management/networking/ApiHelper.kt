package com.clinic.management.networking

import com.clinic.management.model.appointmentslots.AppointmentSlotsResponse
import com.clinic.management.model.appointmments.AppointmentResponse
import com.clinic.management.model.appointmments.CompletedAppointmentResponse
import com.clinic.management.model.category.CategoryResponse
import com.clinic.management.model.doctor.DoctorDetailResponse
import com.clinic.management.model.doctor.DoctorListingResponse
import com.clinic.management.model.home.HomeResponse
import com.clinic.management.model.login.LoginResponse
import com.google.gson.JsonObject
import retrofit2.Response

interface ApiHelper {

    suspend fun login(
        token: String,
        jsonObject: JsonObject
    ): Response<LoginResponse>

    suspend fun register(
        token: String,
        jsonObject: JsonObject
    ): Response<LoginResponse>

    suspend fun home_page(
        token: String,
        jsonObject: JsonObject
    ): Response<HomeResponse>

    suspend fun specialist_doctor_view_all(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorListingResponse>

    suspend fun top_doctor_view_all(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorListingResponse>

    suspend fun doctor_detail(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorDetailResponse>

    suspend fun category_view_all(
        token: String,
        jsonObject: JsonObject
    ): Response<CategoryResponse>

    suspend fun active_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<AppointmentResponse>

    suspend fun completed_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<CompletedAppointmentResponse>

    suspend fun cancel_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<AppointmentResponse>

    suspend fun check_slot_for_doctor(
        token: String,
        jsonObject: JsonObject
    ): Response<AppointmentSlotsResponse>

    suspend fun book_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject>
}