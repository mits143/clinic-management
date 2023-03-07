package com.clinic.management.fragment

import android.os.Bundle
import androidx.navigation.NavDirections
import com.clinic.management.R
import kotlin.Int
import kotlin.String

public class AppointmentFragmentDirections private constructor() {
  private data class ActionNavAppointmentConfirmed(
    public val date: String = "",
    public val time: String = "",
    public val doctorName: String = "",
    public val doctorDegree: String = "",
    public val doctorSpecialization: String = "",
    public val address: String = ""
  ) : NavDirections {
    public override val actionId: Int = R.id.action_nav_appointment_confirmed

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("date", this.date)
        result.putString("time", this.time)
        result.putString("doctor_name", this.doctorName)
        result.putString("doctor_degree", this.doctorDegree)
        result.putString("doctor_specialization", this.doctorSpecialization)
        result.putString("address", this.address)
        return result
      }
  }

  public companion object {
    public fun actionNavAppointmentConfirmed(
      date: String = "",
      time: String = "",
      doctorName: String = "",
      doctorDegree: String = "",
      doctorSpecialization: String = "",
      address: String = ""
    ): NavDirections = ActionNavAppointmentConfirmed(date, time, doctorName, doctorDegree,
        doctorSpecialization, address)
  }
}
