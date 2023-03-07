package com.clinic.management.fragment

import android.os.Bundle
import androidx.navigation.NavDirections
import com.clinic.management.R
import kotlin.Int
import kotlin.String

public class DoctorDetailFragmentDirections private constructor() {
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
    public fun actionNavAppointment(
      doctorId: String = "",
      doctorName: String = "",
      doctorSpecialist: String = "",
      doctorSpecialist1: String = "",
      appointmentId: String = ""
    ): NavDirections = ActionNavAppointment(doctorId, doctorName, doctorSpecialist,
        doctorSpecialist1, appointmentId)
  }
}
