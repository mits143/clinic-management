package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.appointmments.CancelAppointmentResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class CancelAppointmentViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setCancelAppointmentListData =
        MutableLiveData<Event<Resource<CancelAppointmentResponse>>>()
    val getCancelAppointmenListtData: LiveData<Event<Resource<CancelAppointmentResponse>>>
        get() = setCancelAppointmentListData

    fun fetchCancelAppointmenListData(
        token: String, pagination: String
    ) {
        viewModelScope.launch {
            setCancelAppointmentListData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("pagination", pagination)
                mainRepository.cancel_appointment_list(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setCancelAppointmentListData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setCancelAppointmentListData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setCancelAppointmentListData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setCancelAppointmentListData.postValue(
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