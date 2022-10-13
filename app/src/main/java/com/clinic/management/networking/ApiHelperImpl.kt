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

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun login(
        token: String,
        jsonObject: JsonObject
    ): Response<LoginResponse> =
        apiService.login(token, jsonObject)

    override suspend fun register(
        token: String,
        jsonObject: JsonObject
    ): Response<LoginResponse> =
        apiService.register(token, jsonObject)

    override suspend fun home_page(
        token: String,
        jsonObject: JsonObject
    ): Response<HomeResponse> =
        apiService.home_page(token, jsonObject)

    override suspend fun specialist_doctor_view_all(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorListingResponse> =
        apiService.specialist_doctor_view_all(token, jsonObject)

    override suspend fun top_doctor_view_all(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorListingResponse> =
        apiService.top_doctor_view_all(token, jsonObject)

    override suspend fun doctor_detail(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorDetailResponse> =
        apiService.doctor_detail(token, jsonObject)

    override suspend fun category_view_all(
        token: String,
        jsonObject: JsonObject
    ): Response<CategoryResponse> =
        apiService.category_view_all(token, jsonObject)

    override suspend fun active_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<AppointmentResponse> =
        apiService.active_appointment(token, jsonObject)

    override suspend fun completed_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<CompletedAppointmentResponse> =
        apiService.completed_appointment(token, jsonObject)

    override suspend fun cancel_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<AppointmentResponse> =
        apiService.cancel_appointment(token, jsonObject)

    override suspend fun check_slot_for_doctor(
        token: String,
        jsonObject: JsonObject
    ): Response<AppointmentSlotsResponse> =
        apiService.check_slot_for_doctor(token, jsonObject)

    override suspend fun book_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject> =
        apiService.book_appointment(token, jsonObject)
}