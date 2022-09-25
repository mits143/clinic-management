package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import kotlinx.coroutines.launch

class CommonViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setData = MutableLiveData<Resource<ArrayList<String>>>()
    val getData: LiveData<Resource<ArrayList<String>>>
        get() = setData

    fun fetchData() {
        viewModelScope.launch {
            setData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val list = arrayListOf<String>()
                for (i in 0 until 20)
                    list.add("" + i)
                setData.postValue(Resource.success(list))
            } else setData.postValue(Resource.error("No internet connection", null))
        }
    }
}