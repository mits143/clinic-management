package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.category.CategoryResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class FilterViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setData = MutableLiveData< Event<Resource<ArrayList<String>>>>()
    val getData: LiveData<Event<Resource<ArrayList<String>>>>
        get() = setData

    private val setCategoryData = MutableLiveData<Event<Resource<CategoryResponse>>>()
    val getCategoryData: LiveData<Event<Resource<CategoryResponse>>>
        get() = setCategoryData

    fun fetchFilterList() {
        viewModelScope.launch {
            setData.postValue(Event(Resource.loading(null)))
            val list = arrayListOf<String>()
            list.add("By Speciality")
            list.add("By Rating")
            list.add("By Distance")
            setData.postValue(Event(Resource.success(list)))
        }
    }



    fun fetchCategoryData(
        token: String, lat: String, lng: String
    ) {
        viewModelScope.launch {
            setCategoryData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("lat", lat)
                jsonObject.addProperty("lng", lng)
                mainRepository.category_view_all(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setCategoryData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setCategoryData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setCategoryData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setCategoryData.postValue(Event(Resource.error("No internet connection", null)))
        }
    }
}