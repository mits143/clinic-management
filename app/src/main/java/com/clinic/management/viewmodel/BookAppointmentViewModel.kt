package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.appointmentslots.AppointmentSlotsResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class BookAppointmentViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setAppointmentSlotsData =
        MutableLiveData<Event<Resource<AppointmentSlotsResponse>>>()
    val getAppointmentSlotsData: LiveData<Event<Resource<AppointmentSlotsResponse>>>
        get() = setAppointmentSlotsData

    private val setBookAppointmentData = MutableLiveData<Event<Resource<JsonObject>>>()
    val getBookAppointmentData: LiveData<Event<Resource<JsonObject>>>
        get() = setBookAppointmentData

    fun fetchAppointmentSlotsData(
        token: String, doctor_id: String, date: String
    ) {
        viewModelScope.launch {
            setAppointmentSlotsData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("doctor_id", doctor_id)
                jsonObject.addProperty("date", date)
                mainRepository.check_slot_for_doctor(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setAppointmentSlotsData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setAppointmentSlotsData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setAppointmentSlotsData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setAppointmentSlotsData.postValue(
                Event(
                    Resource.error(
                        "No internet connection",
                        null
                    )
                )
            )
        }
    }

    fun fetchBookAppointmentData(
        token: String, doctor_id: String, appointment_date: String, appointment_time: String
    ) {
        viewModelScope.launch {
            setBookAppointmentData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("doctor_id", doctor_id)
                jsonObject.addProperty("appointment_date", appointment_date)
                jsonObject.addProperty("appointment_time", appointment_time)
                mainRepository.book_appointment(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("status")?.asBoolean!!) {
                            setBookAppointmentData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setBookAppointmentData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.get("message")?.asString!!, null
                                    )
                                )
                            )
                        }
                    } else setBookAppointmentData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setBookAppointmentData.postValue(
                Event(
                    Resource.error(
                        "No internet connection",
                        null
                    )
                )
            )
        }
    }

    fun fetchRescheduleAppointmentData(
        token: String, doctor_id: String, appointment_date: String, appointment_time: String
    ) {
        viewModelScope.launch {
            setBookAppointmentData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("appointment_id", doctor_id)
                jsonObject.addProperty("appointment_date", appointment_date)
                jsonObject.addProperty("appointment_time", appointment_time)
                mainRepository.reschdule_appointment(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("status")?.asBoolean!!) {
                            setBookAppointmentData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setBookAppointmentData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.get("message")?.asString!!, null
                                    )
                                )
                            )
                        }
                    } else setBookAppointmentData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setBookAppointmentData.postValue(
                Event(
                    Resource.error(
                        "No internet connection",
                        null
                    )
                )
            )
        }
    }
}