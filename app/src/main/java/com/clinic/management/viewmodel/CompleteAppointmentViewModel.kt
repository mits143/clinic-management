package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.appointmments.CompletedAppointmentResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class CompleteAppointmentViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setCompleteAppointmentData =
        MutableLiveData<Resource<CompletedAppointmentResponse>>()
    val getCompleteAppointmentData: LiveData<Resource<CompletedAppointmentResponse>>
        get() = setCompleteAppointmentData

    fun fetchCompleteAppointmentData(
        token: String, pagination: String
    ) {
        viewModelScope.launch {
            setCompleteAppointmentData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("pagination", pagination)
                mainRepository.completed_appointment(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setCompleteAppointmentData.postValue(Resource.success(it.body()))
                        } else {
                            setCompleteAppointmentData.postValue(
                                Resource.error(
                                    it.body()?.message!!, null
                                )
                            )
                        }
                    } else setCompleteAppointmentData.postValue(
                        Resource.error(
                            it.message(), null
                        )
                    )
                }
            } else setCompleteAppointmentData.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }
}