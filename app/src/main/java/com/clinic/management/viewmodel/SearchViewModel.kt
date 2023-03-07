package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.doctor.DoctorListingResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class SearchViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setDoctorData = MutableLiveData<Event<Resource<DoctorListingResponse>>>()
    val getDoctorData: LiveData<Event<Resource<DoctorListingResponse>>>
        get() = setDoctorData

    fun fetchSearchResultData(
        token: String,
        lat: String,
        lng: String,
        string: String,
        specialization: String,
        rating: String,
        distance: String,
        pagination: String
    ) {
        viewModelScope.launch {
            setDoctorData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("lat", lat)
                jsonObject.addProperty("lng", lng)
                jsonObject.addProperty("string", string)
                jsonObject.addProperty("specialization", specialization)
                jsonObject.addProperty("rating", rating)
                jsonObject.addProperty("distance", distance)
                jsonObject.addProperty("pagination", pagination)
                mainRepository.get_search_result(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setDoctorData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setDoctorData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setDoctorData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setDoctorData.postValue(Event(Resource.error("No internet connection", null)))
        }
    }
}