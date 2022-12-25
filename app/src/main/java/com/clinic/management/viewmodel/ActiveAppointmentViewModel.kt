package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.appointmments.ActiveAppointmentResponse
import com.clinic.management.model.appointmments.CancelAppointmentResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class ActiveAppointmentViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setAppointmentData = MutableLiveData<Event<Resource<ActiveAppointmentResponse>>>()
    val getAppointmentData: LiveData<Event<Resource<ActiveAppointmentResponse>>>
        get() = setAppointmentData

    private val setCancelAppointmentListData =
        MutableLiveData<Event<Resource<CancelAppointmentResponse>>>()
    val getCancelAppointmenListtData: LiveData<Event<Resource<CancelAppointmentResponse>>>
        get() = setCancelAppointmentListData

    private val setAppointmentCancelData = MutableLiveData<Event<Resource<JsonObject>>>()
    val getAppointmenCanceltData: LiveData<Event<Resource<JsonObject>>>
        get() = setAppointmentCancelData

    fun fetchActiveAppointmentData(
        token: String, pagination: String
    ) {
        viewModelScope.launch {
            setAppointmentData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("pagination", pagination)
                mainRepository.active_appointment(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setAppointmentData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setAppointmentData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setAppointmentData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setAppointmentData.postValue(
                Event(
                    Resource.error(
                        "No internet connection",
                        null
                    )
                )
            )
        }
    }

    fun fetchCancelAppointmenData(
        token: String, appointment_id: String
    ) {
        viewModelScope.launch {
            setAppointmentCancelData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("appointment_id", appointment_id)
                mainRepository.cancel_appointment(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("status")?.asBoolean!!) {
                            setAppointmentCancelData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setAppointmentCancelData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.get("message")?.asString!!, null
                                    )
                                )
                            )
                        }
                    } else setAppointmentCancelData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setAppointmentCancelData.postValue(
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