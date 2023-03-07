package com.clinic.management.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.clinic.management.model.lab.BookingInformation
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class LabRadiologyResultFragmentArgs(
  public val type: Int = 0,
  public val labData: BookingInformation? = null,
  public val radiologyData: com.clinic.management.model.radiology.BookingInformation? = null
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("type", this.type)
    if (Parcelable::class.java.isAssignableFrom(BookingInformation::class.java)) {
      result.putParcelable("lab_data", this.labData as Parcelable?)
    } else if (Serializable::class.java.isAssignableFrom(BookingInformation::class.java)) {
      result.putSerializable("lab_data", this.labData as Serializable?)
    }
    if
        (Parcelable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)) {
      result.putParcelable("radiology_data", this.radiologyData as Parcelable?)
    } else if
        (Serializable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)) {
      result.putSerializable("radiology_data", this.radiologyData as Serializable?)
    }
    return result
  }

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("type", this.type)
    if (Parcelable::class.java.isAssignableFrom(BookingInformation::class.java)) {
      result.set("lab_data", this.labData as Parcelable?)
    } else if (Serializable::class.java.isAssignableFrom(BookingInformation::class.java)) {
      result.set("lab_data", this.labData as Serializable?)
    }
    if
        (Parcelable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)) {
      result.set("radiology_data", this.radiologyData as Parcelable?)
    } else if
        (Serializable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)) {
      result.set("radiology_data", this.radiologyData as Serializable?)
    }
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): LabRadiologyResultFragmentArgs {
      bundle.setClassLoader(LabRadiologyResultFragmentArgs::class.java.classLoader)
      val __type : Int
      if (bundle.containsKey("type")) {
        __type = bundle.getInt("type")
      } else {
        __type = 0
      }
      val __labData : BookingInformation?
      if (bundle.containsKey("lab_data")) {
        if (Parcelable::class.java.isAssignableFrom(BookingInformation::class.java) ||
            Serializable::class.java.isAssignableFrom(BookingInformation::class.java)) {
          __labData = bundle.get("lab_data") as BookingInformation?
        } else {
          throw UnsupportedOperationException(BookingInformation::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
      } else {
        __labData = null
      }
      val __radiologyData : com.clinic.management.model.radiology.BookingInformation?
      if (bundle.containsKey("radiology_data")) {
        if
            (Parcelable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)
            ||
            Serializable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)) {
          __radiologyData =
              bundle.get("radiology_data") as com.clinic.management.model.radiology.BookingInformation?
        } else {
          throw UnsupportedOperationException(com.clinic.management.model.radiology.BookingInformation::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
      } else {
        __radiologyData = null
      }
      return LabRadiologyResultFragmentArgs(__type, __labData, __radiologyData)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        LabRadiologyResultFragmentArgs {
      val __type : Int?
      if (savedStateHandle.contains("type")) {
        __type = savedStateHandle["type"]
        if (__type == null) {
          throw IllegalArgumentException("Argument \"type\" of type integer does not support null values")
        }
      } else {
        __type = 0
      }
      val __labData : BookingInformation?
      if (savedStateHandle.contains("lab_data")) {
        if (Parcelable::class.java.isAssignableFrom(BookingInformation::class.java) ||
            Serializable::class.java.isAssignableFrom(BookingInformation::class.java)) {
          __labData = savedStateHandle.get<BookingInformation?>("lab_data")
        } else {
          throw UnsupportedOperationException(BookingInformation::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
      } else {
        __labData = null
      }
      val __radiologyData : com.clinic.management.model.radiology.BookingInformation?
      if (savedStateHandle.contains("radiology_data")) {
        if
            (Parcelable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)
            ||
            Serializable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)) {
          __radiologyData =
              savedStateHandle.get<com.clinic.management.model.radiology.BookingInformation?>("radiology_data")
        } else {
          throw UnsupportedOperationException(com.clinic.management.model.radiology.BookingInformation::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
      } else {
        __radiologyData = null
      }
      return LabRadiologyResultFragmentArgs(__type, __labData, __radiologyData)
    }
  }
}
