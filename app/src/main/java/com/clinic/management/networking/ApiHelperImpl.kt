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
    ): Response<ActiveAppointmentResponse> =
        apiService.active_appointment(token, jsonObject)

    override suspend fun completed_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<CompletedAppointmentResponse> =
        apiService.completed_appointment(token, jsonObject)

    override suspend fun cancel_appointment_list(
        token: String,
        jsonObject: JsonObject
    ): Response<CancelAppointmentResponse> =
        apiService.cancel_appointment_list(token, jsonObject)

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

    override suspend fun get_pages(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject> =
        apiService.get_pages(token, jsonObject)

    override suspend fun category_wise_doctor_listing(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorListingResponse> =
        apiService.category_wise_doctor_listing(token, jsonObject)

    override suspend fun cancel_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject> =
        apiService.cancel_appointment(token, jsonObject)

    override suspend fun reschdule_appointment(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject> =
        apiService.reschdule_appointment(token, jsonObject)

    override suspend fun medicine_listing(
        token: String,
        jsonObject: JsonObject
    ): Response<MedicineResponse> =
        apiService.medicine_listing(token, jsonObject)

    override suspend fun medicine_detail(
        token: String,
        jsonObject: JsonObject
    ): Response<MedicineDetailResponse> =
        apiService.medicine_detail(token, jsonObject)

    override suspend fun appointment_detail(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorAppointmentResponse> =
        apiService.appointment_detail(token, jsonObject)

    override suspend fun radiology_scan_result(
        token: String,
        jsonObject: JsonObject
    ): Response<RadiologyResultResponse> =
        apiService.radiology_scan_result(token, jsonObject)

    override suspend fun lab_result(
        token: String,
        jsonObject: JsonObject
    ): Response<LabResultResponse> =
        apiService.lab_result(token, jsonObject)

    override suspend fun upload_radiology_result(
        token: String,
        appointment_id: RequestBody,
        radiology_note: RequestBody,
        radiology_lab_result: ArrayList<MultipartBody.Part>,
    ): Response<JsonObject> =
        apiService.upload_radiology_result(
            token,
            appointment_id,
            radiology_note,
            radiology_lab_result
        )

    override suspend fun upload_pathology_result(
        token: String,
        appointment_id: RequestBody,
        pathology_note: RequestBody,
        pathology_lab_result: ArrayList<MultipartBody.Part>,
    ): Response<JsonObject> =
        apiService.upload_pathology_result(
            token,
            appointment_id,
            pathology_note,
            pathology_lab_result
        )

    override suspend fun submit_review(
        token: String,
        jsonObject: JsonObject
    ): Response<JsonObject> =
        apiService.submit_review(token, jsonObject)

    override suspend fun get_search_result(
        token: String,
        jsonObject: JsonObject
    ): Response<DoctorListingResponse> =
        apiService.get_search_result(token, jsonObject)
}