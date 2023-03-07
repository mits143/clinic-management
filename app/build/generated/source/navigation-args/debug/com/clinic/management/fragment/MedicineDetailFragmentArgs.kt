package com.clinic.management.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class MedicineDetailFragmentArgs(
  public val medicineId: String = "0"
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("medicine_id", this.medicineId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("medicine_id", this.medicineId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MedicineDetailFragmentArgs {
      bundle.setClassLoader(MedicineDetailFragmentArgs::class.java.classLoader)
      val __medicineId : String?
      if (bundle.containsKey("medicine_id")) {
        __medicineId = bundle.getString("medicine_id")
        if (__medicineId == null) {
          throw IllegalArgumentException("Argument \"medicine_id\" is marked as non-null but was passed a null value.")
        }
      } else {
        __medicineId = "0"
      }
      return MedicineDetailFragmentArgs(__medicineId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        MedicineDetailFragmentArgs {
      val __medicineId : String?
      if (savedStateHandle.contains("medicine_id")) {
        __medicineId = savedStateHandle["medicine_id"]
        if (__medicineId == null) {
          throw IllegalArgumentException("Argument \"medicine_id\" is marked as non-null but was passed a null value")
        }
      } else {
        __medicineId = "0"
      }
      return MedicineDetailFragmentArgs(__medicineId)
    }
  }
}
