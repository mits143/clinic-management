package com.clinic.management.util

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val ACCESS_TOKEN = "access_token"
    private val USERNAME = "username"
    private val USERIMAGE = "userimage"
    private val CITY = "city"
    private val LATITUDE = "latitude"
    private val LONGITUDE = "longitude"

    private val preferences: SharedPreferences =
        context.getSharedPreferences("alhanpos_pref", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN, "")
        set(value) = preferences.edit().putString(ACCESS_TOKEN, value).apply()

    var userName: String?
        get() = preferences.getString(USERNAME, "")
        set(value) = preferences.edit().putString(USERNAME, value).apply()

    var userImage: String?
        get() = preferences.getString(USERIMAGE, "")
        set(value) = preferences.edit().putString(USERIMAGE, value).apply()

    var city: String?
        get() = preferences.getString(CITY, "Select Location")
        set(value) = preferences.edit().putString(CITY, value).apply()

    var latitude: String?
        get() = preferences.getString(LATITUDE, "")
        set(value) = preferences.edit().putString(LATITUDE, value).apply()

    var longitude: String?
        get() = preferences.getString(LONGITUDE, "")
        set(value) = preferences.edit().putString(LONGITUDE, value).apply()
}