package com.clinic.management.networking

import com.clinic.management.model.appointmentslots.AppointmentSlotsResponse
import com.clinic.management.model.appointmments.ActiveAppointmentResponse
import com.clinic.management.model.appointmments.CancelAppointmentResponse
import com.clinic.management.model.appointmments.CompletedAppointmentResponse
import com.clinic.management.model.category.CategoryResponse
import com.clinic.management.model.doctor.DoctorDetailResponse
import com.clinic.management.model.doctor.DoctorListingResponse
import com.clinic.management.model.doctorResult.DoctorAppointmentResponse
import com.clinic.management.model.home.HomeResponse
import com.clinic.management.model.lab.LabResultResponse
import com.clinic.management.model.login.LoginResponse
import com.clinic.management.model.medicine.MedicineDetailResponse
import com.clinic.management.model.medicine.MedicineResponse
import com.clinic.management.model.radiology.RadiologyResultResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    ): Response<ActiveAppointmentResponse>

    suspend fun completed_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<CompletedAppointmentResponse>

    suspend fun cancel_appointment_list(
        token: String,
        jsonObject: JsonObject
    ): Response<CancelAppointmentResponse>

    suspend fun check_slot_for_doctor(
        token: String,
        jsonObject: JsonObject
    ): Response<AppointmentSlotsResponse>

    suspend fun book_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject>

    suspend fun get_pages(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject>

    suspend fun category_wise_doctor_listing(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorListingResponse>

    suspend fun cancel_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject>

    suspend fun reschdule_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject>

    suspend fun medicine_listing(
        token: String,
        jsonObject: JsonObject
    ): Response<MedicineResponse>

    suspend fun medicine_detail(
        token: String,
        jsonObject: JsonObject
    ): Response<MedicineDetailResponse>

    suspend fun appointment_detail(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorAppointmentResponse>

    suspend fun radiology_scan_result(
        token: String,
        jsonObject: JsonObject
    ): Response<RadiologyResultResponse>

    suspend fun lab_result(
        token: String,
        jsonObject: JsonObject
    ): Response<LabResultResponse>

    suspend fun upload_radiology_result(
        token: String,
        appointment_id: RequestBody,
        radiology_note: RequestBody,
        radiology_lab_result: ArrayList<MultipartBody.Part>,
    ): Response<JsonObject>

    suspend fun upload_pathology_result(
        token: String,
        appointment_id: RequestBody,
        pathology_note: RequestBody,
        pathology_lab_result: ArrayList<MultipartBody.Part>,
    ): Response<JsonObject>

    suspend fun submit_review(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject>

    suspend fun get_search_result(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorListingResponse>
}