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
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DoctorAppointmentViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setAppointmentData = MutableLiveData<Event<Resource<DoctorAppointmentResponse>>>()
    val getAppointmentData: LiveData<Event<Resource<DoctorAppointmentResponse>>>
        get() = setAppointmentData

    private val setUpload = MutableLiveData<Event<Resource<JsonObject>>>()
    val getUpload: LiveData<Event<Resource<JsonObject>>>
        get() = setUpload

    private val setRateDoctor = MutableLiveData<Event<Resource<JsonObject>>>()
    val getRateDoctor: LiveData<Event<Resource<JsonObject>>>
        get() = setRateDoctor

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
                        "No internet connection", null
                    )
                )
            )
        }
    }

    fun fetchUploadRadiologyData(
        token: String,
        appointment_id: RequestBody,
        radiology_note: RequestBody,
        radiology_lab_result: ArrayList<MultipartBody.Part>,
    ) {
        viewModelScope.launch {
            setUpload.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.upload_radiology_result(
                    token, appointment_id, radiology_note, radiology_lab_result
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("status")?.asBoolean!!) {
                            setUpload.postValue(Event(Resource.success(it.body())))
                        } else {
                            setUpload.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.get("message")?.asString!!, null
                                    )
                                )
                            )
                        }
                    } else setUpload.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setUpload.postValue(
                Event(
                    Resource.error(
                        "No internet connection", null
                    )
                )
            )
        }
    }

    fun fetchUploadPathologyData(
        token: String,
        appointment_id: RequestBody,
        pathology_note: RequestBody,
        pathology_lab_result: ArrayList<MultipartBody.Part>,
    ) {
        viewModelScope.launch {
            setUpload.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.upload_pathology_result(
                    token, appointment_id, pathology_note, pathology_lab_result
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("status")?.asBoolean!!) {
                            setUpload.postValue(Event(Resource.success(it.body())))
                        } else {
                            setUpload.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.get("message")?.asString!!, null
                                    )
                                )
                            )
                        }
                    } else setUpload.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setUpload.postValue(
                Event(
                    Resource.error(
                        "No internet connection", null
                    )
                )
            )
        }
    }

    fun fetchRateDoctor(
        token: String,
        rating: String,
        comment: String,
        appointment_id: String,
    ) {
        viewModelScope.launch {
            setRateDoctor.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("rating", rating)
                jsonObject.addProperty("comment", comment)
                jsonObject.addProperty("appointment_id", appointment_id)
                mainRepository.submit_review(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("status")?.asBoolean!!) {
                            setRateDoctor.postValue(Event(Resource.success(it.body())))
                        } else {
                            setRateDoctor.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.get("message")?.asString!!, null
                                    )
                                )
                            )
                        }
                    } else setRateDoctor.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setRateDoctor.postValue(
                Event(
                    Resource.error(
                        "No internet connection", null
                    )
                )
            )
        }
    }
}