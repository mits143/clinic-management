package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.appointmments.AppointmentResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class AppointmentViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setAppointmentData = MutableLiveData<Resource<AppointmentResponse>>()
    val getAppointmentData: LiveData<Resource<AppointmentResponse>>
        get() = setAppointmentData

    fun fetchActiveAppointmentData(
        token: String, pagination: String
    ) {
        viewModelScope.launch {
            setAppointmentData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("pagination", pagination)
                mainRepository.active_appointment(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setAppointmentData.postValue(Resource.success(it.body()))
                        } else {
                            setAppointmentData.postValue(
                                Resource.error(
                                    it.body()?.message!!, null
                                )
                            )
                        }
                    } else setAppointmentData.postValue(
                        Resource.error(
                            it.message(), null
                        )
                    )
                }
            } else setAppointmentData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchCancelAppointmentData(
        token: String, pagination: String
    ) {
        viewModelScope.launch {
            setAppointmentData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("pagination", pagination)
                mainRepository.cancel_appointment(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setAppointmentData.postValue(Resource.success(it.body()))
                        } else {
                            setAppointmentData.postValue(
                                Resource.error(
                                    it.body()?.message!!, null
                                )
                            )
                        }
                    } else setAppointmentData.postValue(
                        Resource.error(
                            it.message(), null
                        )
                    )
                }
            } else setAppointmentData.postValue(Resource.error("No internet connection", null))
        }
    }
}