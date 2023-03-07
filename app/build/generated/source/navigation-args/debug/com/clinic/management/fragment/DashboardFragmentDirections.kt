package com.clinic.management.fragment

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.clinic.management.R
import kotlin.Int

public class DashboardFragmentDirections private constructor() {
  private data class ActionNavMyResult(
    public val currentItem: Int = 0
  ) : NavDirections {
    public override val actionId: Int = R.id.action_nav_my_result

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("currentItem", this.currentItem)
        return result
      }
  }

  public companion object {
    public fun actionNavMyAppointment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_nav_my_appointment)

    public fun actionNavAskADoctor(): NavDirections =
        ActionOnlyNavDirections(R.id.action_nav_ask_a_doctor)

    public fun actionNavMyResult(currentItem: Int = 0): NavDirections =
        ActionNavMyResult(currentItem)

    public fun actionNavMedicine(): NavDirections =
        ActionOnlyNavDirections(R.id.action_nav_medicine)

    public fun actionNavHome(): NavDirections = ActionOnlyNavDirections(R.id.action_nav_home)
  }
}
