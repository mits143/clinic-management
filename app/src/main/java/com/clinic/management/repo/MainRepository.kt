package com.clinic.management.repo

import com.clinic.management.networking.ApiHelper
import com.google.gson.JsonObject

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun login(
        token: String, jsonObject: JsonObject
    ) = apiHelper.login(token, jsonObject)

    suspend fun register(
        token: String, jsonObject: JsonObject
    ) = apiHelper.register(token, jsonObject)

    suspend fun home_page(
        token: String, jsonObject: JsonObject
    ) = apiHelper.home_page(token, jsonObject)

    suspend fun specialist_doctor_view_all(
        token: String, jsonObject: JsonObject
    ) = apiHelper.specialist_doctor_view_all(token, jsonObject)

    suspend fun top_doctor_view_all(
        token: String, jsonObject: JsonObject
    ) = apiHelper.top_doctor_view_all(token, jsonObject)

    suspend fun doctor_detail(
        token: String, jsonObject: JsonObject
    ) = apiHelper.doctor_detail(token, jsonObject)

    suspend fun category_view_all(
        token: String, jsonObject: JsonObject
    ) = apiHelper.category_view_all(token, jsonObject)

    suspend fun active_appointment(
        token: String, jsonObject: JsonObject
    ) = apiHelper.active_appointment(token, jsonObject)

    suspend fun completed_appointment(
        token: String, jsonObject: JsonObject
    ) = apiHelper.completed_appointment(token, jsonObject)

    suspend fun cancel_appointment_list(
        token: String, jsonObject: JsonObject
    ) = apiHelper.cancel_appointment_list(token, jsonObject)

    suspend fun check_slot_for_doctor(
        token: String, jsonObject: JsonObject
    ) = apiHelper.check_slot_for_doctor(token, jsonObject)

    suspend fun book_appointment(
        token: String, jsonObject: JsonObject
    ) = apiHelper.book_appointment(token, jsonObject)

    suspend fun get_pages(
        token: String, jsonObject: JsonObject
    ) = apiHelper.get_pages(token, jsonObject)

    suspend fun category_wise_doctor_listing(
        token: String, jsonObject: JsonObject
    ) = apiHelper.category_wise_doctor_listing(token, jsonObject)

    suspend fun cancel_appointment(
        token: String, jsonObject: JsonObject
    ) = apiHelper.cancel_appointment(token, jsonObject)

    suspend fun reschdule_appointment(
        token: String, jsonObject: JsonObject
    ) = apiHelper.reschdule_appointment(token, jsonObject)

    suspend fun medicine_listing(
        token: String, jsonObject: JsonObject
    ) = apiHelper.medicine_listing(token, jsonObject)

    suspend fun medicine_detail(
        token: String, jsonObject: JsonObject
    ) = apiHelper.medicine_detail(token, jsonObject)

    suspend fun appointment_detail(
        token: String, jsonObject: JsonObject
    ) = apiHelper.appointment_detail(token, jsonObject)
}