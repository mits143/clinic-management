package com.clinic.management.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Boolean
import kotlin.String
import kotlin.jvm.JvmStatic

public data class DoctorListingFragmentArgs(
  public val isTopDoctor: Boolean = false,
  public val categoryID: String = "0"
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putBoolean("isTopDoctor", this.isTopDoctor)
    result.putString("categoryID", this.categoryID)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("isTopDoctor", this.isTopDoctor)
    result.set("categoryID", this.categoryID)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): DoctorListingFragmentArgs {
      bundle.setClassLoader(DoctorListingFragmentArgs::class.java.classLoader)
      val __isTopDoctor : Boolean
      if (bundle.containsKey("isTopDoctor")) {
        __isTopDoctor = bundle.getBoolean("isTopDoctor")
      } else {
        __isTopDoctor = false
      }
      val __categoryID : String?
      if (bundle.containsKey("categoryID")) {
        __categoryID = bundle.getString("categoryID")
        if (__categoryID == null) {
          throw IllegalArgumentException("Argument \"categoryID\" is marked as non-null but was passed a null value.")
        }
      } else {
        __categoryID = "0"
      }
      return DoctorListingFragmentArgs(__isTopDoctor, __categoryID)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DoctorListingFragmentArgs {
      val __isTopDoctor : Boolean?
      if (savedStateHandle.contains("isTopDoctor")) {
        __isTopDoctor = savedStateHandle["isTopDoctor"]
        if (__isTopDoctor == null) {
          throw IllegalArgumentException("Argument \"isTopDoctor\" of type boolean does not support null values")
        }
      } else {
        __isTopDoctor = false
      }
      val __categoryID : String?
      if (savedStateHandle.contains("categoryID")) {
        __categoryID = savedStateHandle["categoryID"]
        if (__categoryID == null) {
          throw IllegalArgumentException("Argument \"categoryID\" is marked as non-null but was passed a null value")
        }
      } else {
        __categoryID = "0"
      }
      return DoctorListingFragmentArgs(__isTopDoctor, __categoryID)
    }
  }
}
