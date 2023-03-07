package com.clinic.management.fragment

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.clinic.management.R

public class AppointmentConfirmedFragmentDirections private constructor() {
  public companion object {
    public fun actionNavHome(): NavDirections = ActionOnlyNavDirections(R.id.action_nav_home)
  }
}
