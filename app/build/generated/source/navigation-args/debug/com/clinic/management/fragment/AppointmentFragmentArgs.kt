package com.clinic.management.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class AppointmentFragmentArgs(
  public val doctorId: String = "",
  public val doctorName: String = "",
  public val doctorSpecialist: String = "",
  public val doctorSpecialist1: String = "",
  public val appointmentId: String = ""
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("doctor_id", this.doctorId)
    result.putString("doctor_name", this.doctorName)
    result.putString("doctor_specialist", this.doctorSpecialist)
    result.putString("doctor_specialist1", this.doctorSpecialist1)
    result.putString("appointment_id", this.appointmentId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("doctor_id", this.doctorId)
    result.set("doctor_name", this.doctorName)
    result.set("doctor_specialist", this.doctorSpecialist)
    result.set("doctor_specialist1", this.doctorSpecialist1)
    result.set("appointment_id", this.appointmentId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): AppointmentFragmentArgs {
      bundle.setClassLoader(AppointmentFragmentArgs::class.java.classLoader)
      val __doctorId : String?
      if (bundle.containsKey("doctor_id")) {
        __doctorId = bundle.getString("doctor_id")
        if (__doctorId == null) {
          throw IllegalArgumentException("Argument \"doctor_id\" is marked as non-null but was passed a null value.")
        }
      } else {
        __doctorId = ""
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
      val __doctorSpecialist : String?
      if (bundle.containsKey("doctor_specialist")) {
        __doctorSpecialist = bundle.getString("doctor_specialist")
        if (__doctorSpecialist == null) {
          throw IllegalArgumentException("Argument \"doctor_specialist\" is marked as non-null but was passed a null value.")
        }
      } else {
        __doctorSpecialist = ""
      }
      val __doctorSpecialist1 : String?
      if (bundle.containsKey("doctor_specialist1")) {
        __doctorSpecialist1 = bundle.getString("doctor_specialist1")
        if (__doctorSpecialist1 == null) {
          throw IllegalArgumentException("Argument \"doctor_specialist1\" is marked as non-null but was passed a null value.")
        }
      } else {
        __doctorSpecialist1 = ""
      }
      val __appointmentId : String?
      if (bundle.containsKey("appointment_id")) {
        __appointmentId = bundle.getString("appointment_id")
        if (__appointmentId == null) {
          throw IllegalArgumentException("Argument \"appointment_id\" is marked as non-null but was passed a null value.")
        }
      } else {
        __appointmentId = ""
      }
      return AppointmentFragmentArgs(__doctorId, __doctorName, __doctorSpecialist,
          __doctorSpecialist1, __appointmentId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AppointmentFragmentArgs {
      val __doctorId : String?
      if (savedStateHandle.contains("doctor_id")) {
        __doctorId = savedStateHandle["doctor_id"]
        if (__doctorId == null) {
          throw IllegalArgumentException("Argument \"doctor_id\" is marked as non-null but was passed a null value")
        }
      } else {
        __doctorId = ""
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
      val __doctorSpecialist : String?
      if (savedStateHandle.contains("doctor_specialist")) {
        __doctorSpecialist = savedStateHandle["doctor_specialist"]
        if (__doctorSpecialist == null) {
          throw IllegalArgumentException("Argument \"doctor_specialist\" is marked as non-null but was passed a null value")
        }
      } else {
        __doctorSpecialist = ""
      }
      val __doctorSpecialist1 : String?
      if (savedStateHandle.contains("doctor_specialist1")) {
        __doctorSpecialist1 = savedStateHandle["doctor_specialist1"]
        if (__doctorSpecialist1 == null) {
          throw IllegalArgumentException("Argument \"doctor_specialist1\" is marked as non-null but was passed a null value")
        }
      } else {
        __doctorSpecialist1 = ""
      }
      val __appointmentId : String?
      if (savedStateHandle.contains("appointment_id")) {
        __appointmentId = savedStateHandle["appointment_id"]
        if (__appointmentId == null) {
          throw IllegalArgumentException("Argument \"appointment_id\" is marked as non-null but was passed a null value")
        }
      } else {
        __appointmentId = ""
      }
      return AppointmentFragmentArgs(__doctorId, __doctorName, __doctorSpecialist,
          __doctorSpecialist1, __appointmentId)
    }
  }
}
