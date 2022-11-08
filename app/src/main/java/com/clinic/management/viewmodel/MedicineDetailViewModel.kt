package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.medicine.MedicineDetailResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class MedicineDetailViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setMedicineDetailData = MutableLiveData<Event<Resource<MedicineDetailResponse>>>()
    val getMedicineDetailData: LiveData<Event<Resource<MedicineDetailResponse>>>
        get() = setMedicineDetailData

    fun fetchMedicineDetailData(
        token: String, medicine_id: String
    ) {
        viewModelScope.launch {
            setMedicineDetailData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("medicine_id", medicine_id)
                mainRepository.medicine_detail(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setMedicineDetailData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setMedicineDetailData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setMedicineDetailData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setMedicineDetailData.postValue(
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