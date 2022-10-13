package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.category.CategoryResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class CategoryListingViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setCategoryData = MutableLiveData<Resource<CategoryResponse>>()
    val getCategoryData: LiveData<Resource<CategoryResponse>>
        get() = setCategoryData

    fun fetchCategoryData(
        token: String, lat: String, lng: String
    ) {
        viewModelScope.launch {
            setCategoryData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("lat", lat)
                jsonObject.addProperty("lng", lng)
                mainRepository.category_view_all(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setCategoryData.postValue(Resource.success(it.body()))
                        } else {
                            setCategoryData.postValue(
                                Resource.error(
                                    it.body()?.message!!, null
                                )
                            )
                        }
                    } else setCategoryData.postValue(
                        Resource.error(
                            it.message(), null
                        )
                    )
                }
            } else setCategoryData.postValue(Resource.error("No internet connection", null))
        }
    }
}