package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.lab.LabResultResponse
import com.clinic.management.model.radiology.RadiologyResultResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class Lab_RadiologyViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setLabResultData = MutableLiveData<Event<Resource<LabResultResponse>>>()
    val getLabResultData: LiveData<Event<Resource<LabResultResponse>>>
        get() = setLabResultData

    private val setRadiologyData = MutableLiveData<Event<Resource<RadiologyResultResponse>>>()
    val getRadiologyData: LiveData<Event<Resource<RadiologyResultResponse>>>
        get() = setRadiologyData

    fun fetchLabResultData(
        token: String, pagination: String
    ) {
        viewModelScope.launch {
            setLabResultData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("pagination", pagination)
                mainRepository.lab_result(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setLabResultData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setLabResultData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setLabResultData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setLabResultData.postValue(
                Event(
                    Resource.error(
                        "No internet connection", null
                    )
                )
            )
        }
    }

    fun fetchRadiologyData(
        token: String, pagination: String
    ) {
        viewModelScope.launch {
            setRadiologyData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("pagination", pagination)
                mainRepository.radiology_scan_result(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setRadiologyData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setRadiologyData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setRadiologyData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setRadiologyData.postValue(
                Event(
                    Resource.error(
                        "No internet connection", null
                    )
                )
            )
        }
    }
}