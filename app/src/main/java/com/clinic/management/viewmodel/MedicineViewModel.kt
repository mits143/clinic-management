package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.medicine.MedicineResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class MedicineViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setMedicineData = MutableLiveData<Event<Resource<MedicineResponse>>>()
    val getMedicineData: LiveData<Event<Resource<MedicineResponse>>>
        get() = setMedicineData

    fun fetchMedicineData(
        token: String, pagination: String
    ) {
        viewModelScope.launch {
            setMedicineData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("pagination", pagination)
                mainRepository.medicine_listing(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setMedicineData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setMedicineData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setMedicineData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setMedicineData.postValue(
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