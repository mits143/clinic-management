package com.clinic.management.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class AppointmentConfirmedFragmentArgs(
  public val date: String = "",
  public val time: String = "",
  public val doctorName: String = "",
  public val doctorDegree: String = "",
  public val doctorSpecialization: String = "",
  public val address: String = ""
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("date", this.date)
    result.putString("time", this.time)
    result.putString("doctor_name", this.doctorName)
    result.putString("doctor_degree", this.doctorDegree)
    result.putString("doctor_specialization", this.doctorSpecialization)
    result.putString("address", this.address)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("date", this.date)
    result.set("time", this.time)
    result.set("doctor_name", this.doctorName)
    result.set("doctor_degree", this.doctorDegree)
    result.set("doctor_specialization", this.doctorSpecialization)
    result.set("address", this.address)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): AppointmentConfirmedFragmentArgs {
      bundle.setClassLoader(AppointmentConfirmedFragmentArgs::class.java.classLoader)
      val __date : String?
      if (bundle.containsKey("date")) {
        __date = bundle.getString("date")
        if (__date == null) {
          throw IllegalArgumentException("Argument \"date\" is marked as non-null but was passed a null value.")
        }
      } else {
        __date = ""
      }
      val __time : String?
      if (bundle.containsKey("time")) {
        __time = bundle.getString("time")
        if (__time == null) {
          throw IllegalArgumentException("Argument \"time\" is marked as non-null but was passed a null value.")
        }
      } else {
        __time = ""
      }
      val __doctorName : String?
      if (bundle.containsKey("doctor_name")) {
        __doctorName = bundle.getString("doctor_name")
        if (__doctorName == null) {
          throw IllegalArgumentException("Argument \"doctor_name\" is marked as non-null but was passed a null value.")
        }
      } else {
        __doctorName = ""
      }
      val __doctorDegree : String?
      if (bundle.containsKey("doctor_degree")) {
        __doctorDegree = bundle.getString("doctor_degree")
        if (__doctorDegree == null) {
          throw IllegalArgumentException("Argument \"doctor_degree\" is marked as non-null but was passed a null value.")
        }
      } else {
        __doctorDegree = ""
      }
      val __doctorSpecialization : String?
      if (bundle.containsKey("doctor_specialization")) {
        __doctorSpecialization = bundle.getString("doctor_specialization")
        if (__doctorSpecialization == null) {
          throw IllegalArgumentException("Argument \"doctor_specialization\" is marked as non-null but was passed a null value.")
        }
      } else {
        __doctorSpecialization = ""
      }
      val __address : String?
      if (bundle.containsKey("address")) {
        __address = bundle.getString("address")
        if (__address == null) {
          throw IllegalArgumentException("Argument \"address\" is marked as non-null but was passed a null value.")
        }
      } else {
        __address = ""
      }
      return AppointmentConfirmedFragmentArgs(__date, __time, __doctorName, __doctorDegree,
          __doctorSpecialization, __address)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        AppointmentConfirmedFragmentArgs {
      val __date : String?
      if (savedStateHandle.contains("date")) {
        __date = savedStateHandle["date"]
        if (__date == null) {
          throw IllegalArgumentException("Argument \"date\" is marked as non-null but was passed a null value")
        }
      } else {
        __date = ""
      }
      val __time : String?
      if (savedStateHandle.contains("time")) {
        __time = savedStateHandle["time"]
        if (__time == null) {
          throw IllegalArgumentException("Argument \"time\" is marked as non-null but was passed a null value")
        }
      } else {
        __time = ""
      }
      val __doctorName : String?
      if (savedStateHandle.contains("doctor_name")) {
        __doctorName = savedStateHandle["doctor_name"]
        if (__doctorName == null) {
          throw IllegalArgumentException("Argument \"doctor_name\" is marked as non-null but was passed a null value")
        }
      } else {
        __doctorName = ""
      }
      val __doctorDegree : String?
      if (savedStateHandle.contains("doctor_degree")) {
        __doctorDegree = savedStateHandle["doctor_degree"]
        if (__doctorDegree == null) {
          throw IllegalArgumentException("Argument \"doctor_degree\" is marked as non-null but was passed a null value")
        }
      } else {
        __doctorDegree = ""
      }
      val __doctorSpecialization : String?
      if (savedStateHandle.contains("doctor_specialization")) {
        __doctorSpecialization = savedStateHandle["doctor_specialization"]
        if (__doctorSpecialization == null) {
          throw IllegalArgumentException("Argument \"doctor_specialization\" is marked as non-null but was passed a null value")
        }
      } else {
        __doctorSpecialization = ""
      }
      val __address : String?
      if (savedStateHandle.contains("address")) {
        __address = savedStateHandle["address"]
        if (__address == null) {
          throw IllegalArgumentException("Argument \"address\" is marked as non-null but was passed a null value")
        }
      } else {
        __address = ""
      }
      return AppointmentConfirmedFragmentArgs(__date, __time, __doctorName, __doctorDegree,
          __doctorSpecialization, __address)
    }
  }
}
