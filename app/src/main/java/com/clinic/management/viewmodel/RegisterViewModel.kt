package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.login.LoginResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.Event
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setLoginData = MutableLiveData<Event<Resource<LoginResponse>>>()
    val getLoginData: LiveData<Event<Resource<LoginResponse>>>
        get() = setLoginData

    fun register(
        token: String,
        email: String,
        password: String,
        name: String,
        phone: String,
        address: String,
        gender: String,
        fcm_no: String,
    ) {
        viewModelScope.launch {
            setLoginData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("name", name)
                jsonObject.addProperty("email", email)
                jsonObject.addProperty("password", password)
                jsonObject.addProperty("phone", phone)
                jsonObject.addProperty("address", address)
                jsonObject.addProperty("gender", gender)
                jsonObject.addProperty("source", "android")
                jsonObject.addProperty("fcm_no", fcm_no)
                mainRepository.register(
                    token,
                    jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setLoginData.postValue(Event(Resource.success(it.body())))
                        } else {
                            setLoginData.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.message!!, null
                                    )
                                )
                            )
                        }
                    } else setLoginData.postValue(
                        Event(
                            Resource.error(
                                it.message(), null
                            )
                        )
                    )
                }
            } else setLoginData.postValue(Event(Resource.error("No internet connection", null)))
        }
    }
}