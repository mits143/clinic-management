package com.clinic.management.fragment

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.clinic.management.R
import kotlin.Boolean
import kotlin.Int
import kotlin.String

public class HomeFragmentDirections private constructor() {
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

  private data class ActionNavDoctorDetail(
    public val doctorId: String = ""
  ) : NavDirections {
    public override val actionId: Int = R.id.action_nav_doctor_detail

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("doctor_id", this.doctorId)
        return result
      }
  }

  private data class ActionNavAppointment(
    public val doctorId: String = "",
    public val doctorName: String = "",
    public val doctorSpecialist: String = "",
    public val doctorSpecialist1: String = "",
    public val appointmentId: String = ""
  ) : NavDirections {
    public override val actionId: Int = R.id.action_nav_appointment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("doctor_id", this.doctorId)
        result.putString("doctor_name", this.doctorName)
        result.putString("doctor_specialist", this.doctorSpecialist)
        result.putString("doctor_specialist1", this.doctorSpecialist1)
        result.putString("appointment_id", this.appointmentId)
        return result
      }
  }

  public companion object {
    public fun actionNavDoctorListing(isTopDoctor: Boolean = false, categoryID: String = "0"):
        NavDirections = ActionNavDoctorListing(isTopDoctor, categoryID)

    public fun actionNavDoctorDetail(doctorId: String = ""): NavDirections =
        ActionNavDoctorDetail(doctorId)

    public fun actionNavAppointment(
      doctorId: String = "",
      doctorName: String = "",
      doctorSpecialist: String = "",
      doctorSpecialist1: String = "",
      appointmentId: String = ""
    ): NavDirections = ActionNavAppointment(doctorId, doctorName, doctorSpecialist,
        doctorSpecialist1, appointmentId)

    public fun actionNavCategory(): NavDirections =
        ActionOnlyNavDirections(R.id.action_nav_category)
  }
}
