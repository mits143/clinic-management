package com.clinic.management.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class DoctorDetailFragmentArgs(
  public val doctorId: String = ""
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("doctor_id", this.doctorId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("doctor_id", this.doctorId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): DoctorDetailFragmentArgs {
      bundle.setClassLoader(DoctorDetailFragmentArgs::class.java.classLoader)
      val __doctorId : String?
      if (bundle.containsKey("doctor_id")) {
        __doctorId = bundle.getString("doctor_id")
        if (__doctorId == null) {
          throw IllegalArgumentException("Argument \"doctor_id\" is marked as non-null but was passed a null value.")
        }
      } else {
        __doctorId = ""
      }
      return DoctorDetailFragmentArgs(__doctorId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DoctorDetailFragmentArgs {
      val __doctorId : String?
      if (savedStateHandle.contains("doctor_id")) {
        __doctorId = savedStateHandle["doctor_id"]
        if (__doctorId == null) {
          throw IllegalArgumentException("Argument \"doctor_id\" is marked as non-null but was passed a null value")
        }
      } else {
        __doctorId = ""
      }
      return DoctorDetailFragmentArgs(__doctorId)
    }
  }
}
