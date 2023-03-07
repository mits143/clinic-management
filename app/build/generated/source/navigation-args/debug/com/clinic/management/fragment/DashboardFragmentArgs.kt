package com.clinic.management.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class DashboardFragmentArgs(
  public val currentItem: Int = 0
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("currentItem", this.currentItem)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("currentItem", this.currentItem)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): DashboardFragmentArgs {
      bundle.setClassLoader(DashboardFragmentArgs::class.java.classLoader)
      val __currentItem : Int
      if (bundle.containsKey("currentItem")) {
        __currentItem = bundle.getInt("currentItem")
      } else {
        __currentItem = 0
      }
      return DashboardFragmentArgs(__currentItem)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DashboardFragmentArgs {
      val __currentItem : Int?
      if (savedStateHandle.contains("currentItem")) {
        __currentItem = savedStateHandle["currentItem"]
        if (__currentItem == null) {
          throw IllegalArgumentException("Argument \"currentItem\" of type integer does not support null values")
        }
      } else {
        __currentItem = 0
      }
      return DashboardFragmentArgs(__currentItem)
    }
  }
}
