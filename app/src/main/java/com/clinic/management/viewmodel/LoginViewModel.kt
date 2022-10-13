package com.clinic.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.management.model.login.LoginResponse
import com.clinic.management.repo.MainRepository
import com.clinic.management.util.NetworkHelper
import com.clinic.management.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class LoginViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setLoginData = MutableLiveData<Resource<LoginResponse>>()
    val getLoginData: LiveData<Resource<LoginResponse>>
        get() = setLoginData

    fun login(
        token: String,
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            setLoginData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("username", username)
                jsonObject.addProperty("password", password)
                mainRepository.login(
                    token,
                    jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status!!) {
                            setLoginData.postValue(Resource.success(it.body()))
                        } else {
                            setLoginData.postValue(
                                Resource.error(
                                    it.body()?.message!!, null
                                )
                            )
                        }
                    } else setLoginData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setLoginData.postValue(Resource.error("No internet connection", null))
        }
    }
}