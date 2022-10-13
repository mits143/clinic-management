package com.clinic.management.networking

import com.clinic.management.model.appointmentslots.AppointmentSlotsResponse
import com.clinic.management.model.appointmments.AppointmentResponse
import com.clinic.management.model.appointmments.CompletedAppointmentResponse
import com.clinic.management.model.category.CategoryResponse
import com.clinic.management.model.doctor.DoctorDetailResponse
import com.clinic.management.model.doctor.DoctorListingResponse
import com.clinic.management.model.home.HomeResponse
import com.clinic.management.model.login.LoginResponse
import com.clinic.management.util.Constants.ACTIVE_APPOINTMENT
import com.clinic.management.util.Constants.BOOK_APPOINTMENT
import com.clinic.management.util.Constants.CANCEL_APPOINTMENT
import com.clinic.management.util.Constants.CATEGORY_VIEW_ALL
import com.clinic.management.util.Constants.CHECK_SLOT_FOR_DOCTOR
import com.clinic.management.util.Constants.COMPLETED_APPOINTMENT
import com.clinic.management.util.Constants.DOCTOR_DETAIL
import com.clinic.management.util.Constants.HOME_PAGE
import com.clinic.management.util.Constants.LOGIN
import com.clinic.management.util.Constants.REGISTER
import com.clinic.management.util.Constants.SPECIALIST_DOCTOR_VIEW_ALL
import com.clinic.management.util.Constants.TOP_DOCTOR_VIEW_ALL
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST(LOGIN)
    suspend fun login(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<LoginResponse>

    @POST(REGISTER)
    suspend fun register(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<LoginResponse>

    @POST(HOME_PAGE)
    suspend fun home_page(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<HomeResponse>

    @POST(SPECIALIST_DOCTOR_VIEW_ALL)
    suspend fun specialist_doctor_view_all(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<DoctorListingResponse>

    @POST(TOP_DOCTOR_VIEW_ALL)
    suspend fun top_doctor_view_all(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<DoctorListingResponse>

    @POST(DOCTOR_DETAIL)
    suspend fun doctor_detail(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<DoctorDetailResponse>

    @POST(CATEGORY_VIEW_ALL)
    suspend fun category_view_all(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<CategoryResponse>

    @POST(ACTIVE_APPOINTMENT)
    suspend fun active_appointment(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<AppointmentResponse>

    @POST(COMPLETED_APPOINTMENT)
    suspend fun completed_appointment(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<CompletedAppointmentResponse>

    @POST(CANCEL_APPOINTMENT)
    suspend fun cancel_appointment(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<AppointmentResponse>

    @POST(CHECK_SLOT_FOR_DOCTOR)
    suspend fun check_slot_for_doctor(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<AppointmentSlotsResponse>

    @POST(BOOK_APPOINTMENT)
    suspend fun book_appointment(
        @Header("token") token: String, @Body jsonObject: JsonObject
    ): Response<JsonObject>
}