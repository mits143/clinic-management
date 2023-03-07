package com.clinic.management.fragment

import android.os.Bundle
import androidx.navigation.NavDirections
import com.clinic.management.R
import kotlin.Boolean
import kotlin.Int
import kotlin.String

public class CategoryListingFragmentDirections private constructor() {
  private data class ActionNavDoctorListing(
    public val isTopDoctor: Boolean = false,
    public val categoryID: String = "0"
  ) : NavDirections {
    public override val actionId: Int = R.id.action_nav_doctor_listing

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putBoolean("isTopDoctor", this.isTopDoctor)
        result.putString("categoryID", this.categoryID)
        return result
      }
  }

  public companion object {
    public fun actionNavDoctorListing(isTopDoctor: Boolean = false, categoryID: String = "0"):
        NavDirections = ActionNavDoctorListing(isTopDoctor, categoryID)
  }
}
