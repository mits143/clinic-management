package com.clinic.management.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class DoctorResultFragmentArgs(
  public val id: String = ""
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("id", this.id)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("id", this.id)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): DoctorResultFragmentArgs {
      bundle.setClassLoader(DoctorResultFragmentArgs::class.java.classLoader)
      val __id : String?
      if (bundle.containsKey("id")) {
        __id = bundle.getString("id")
        if (__id == null) {
          throw IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.")
        }
      } else {
        __id = ""
      }
      return DoctorResultFragmentArgs(__id)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DoctorResultFragmentArgs {
      val __id : String?
      if (savedStateHandle.contains("id")) {
        __id = savedStateHandle["id"]
        if (__id == null) {
          throw IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value")
        }
      } else {
        __id = ""
      }
      return DoctorResultFragmentArgs(__id)
    }
  }
}
