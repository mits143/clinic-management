package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.doctor.DoctorDetailResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class DoctorDetailViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val setDoctorDetailData = MutableLiveData<Event<Resource<DoctorDetailResponse>>>()
    val getDoctorDetailData: LiveData<Event<Resource<DoctorDetailResponse>>>
        get() = setDoctorDetailData

    fun fetchDoctorDetailData(
        token: String, lat: String, lng: String, doctor_id: String
    ) {
        viewModelScope.launch {
            setDoctorDetailData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("lat", lat)
                jsonObject.addProperty("lng", lng)
                jsonObject.addProperty("doctor_id", doctor_id)
                mainRepository.doctor_detail(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setDoctorDetailData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setDoctorDetailData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setDoctorDetailData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setDoctorDetailData.postValue(
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