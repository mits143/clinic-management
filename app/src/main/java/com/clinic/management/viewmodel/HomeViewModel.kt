package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.home.HomeResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setHomeData = MutableLiveData<Event<Resource<HomeResponse>>>()
    val getHomeData: LiveData<Event<Resource<HomeResponse>>>
        get() = setHomeData

    fun fetchHomeData(
        token: String,
        lat: String,
        lng: String
    ) {
        viewModelScope.launch {
            setHomeData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("lat", lat)
                jsonObject.addProperty("lng", lng)
                mainRepository.home_page(
                    token,
                    jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setHomeData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setHomeData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setHomeData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setHomeData.postValue(Event(Resource.error("No internet connection", null)))
        }
    }
}