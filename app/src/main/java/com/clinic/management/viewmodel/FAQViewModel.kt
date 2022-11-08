package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class FAQViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setFAQData = MutableLiveData<Event<Resource<JsonObject>>>()
    val getFAQData: LiveData<Event<Resource<JsonObject>>>
        get() = setFAQData

    fun fetchFAQData(
        token: String
    ) {
        viewModelScope.launch {
            setFAQData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("type", "faq")
                mainRepository.get_pages(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("status")?.asBoolean!!) {
                            setFAQData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setFAQData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.get("message")?.asString!!, null
                                    )
                                )
                            )
                        }
                    } else setFAQData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setFAQData.postValue(Event(Resource.error("No internet connection", null)))
        }
    }
}