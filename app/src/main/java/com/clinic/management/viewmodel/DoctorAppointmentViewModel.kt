package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.doctorResult.DoctorAppointmentResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class DoctorAppointmentViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setAppointmentData = MutableLiveData<Event<Resource<DoctorAppointmentResponse>>>()
    val getAppointmentData: LiveData<Event<Resource<DoctorAppointmentResponse>>>
        get() = setAppointmentData

    fun fetchAppointmentData(
        token: String, appointment_id: String
    ) {
        viewModelScope.launch {
            setAppointmentData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("appointment_id", appointment_id)
                mainRepository.appointment_detail(
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
}