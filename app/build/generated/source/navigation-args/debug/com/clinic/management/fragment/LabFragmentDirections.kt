package com.clinic.management.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.clinic.management.R
import com.clinic.management.model.lab.BookingInformation
import java.io.Serializable
import kotlin.Int
import kotlin.Suppress

public class LabFragmentDirections private constructor() {
  private data class ActionNavLabRadiologyResult(
    public val type: Int = 0,
    public val labData: BookingInformation? = null,
    public val radiologyData: com.clinic.management.model.radiology.BookingInformation? = null
  ) : NavDirections {
    public override val actionId: Int = R.id.action_nav_lab_radiology_result

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
        result.putInt("type", this.type)
        if (Parcelable::class.java.isAssignableFrom(BookingInformation::class.java)) {
          result.putParcelable("lab_data", this.labData as Parcelable?)
        } else if (Serializable::class.java.isAssignableFrom(BookingInformation::class.java)) {
          result.putSerializable("lab_data", this.labData as Serializable?)
        }
        if
            (Parcelable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)) {
          result.putParcelable("radiology_data", this.radiologyData as Parcelable?)
        } else if
            (Serializable::class.java.isAssignableFrom(com.clinic.management.model.radiology.BookingInformation::class.java)) {
          result.putSerializable("radiology_data", this.radiologyData as Serializable?)
        }
        return result
      }
  }

  public companion object {
    public fun actionNavLabRadiologyResult(
      type: Int = 0,
      labData: BookingInformation? = null,
      radiologyData: com.clinic.management.model.radiology.BookingInformation? = null
    ): NavDirections = ActionNavLabRadiologyResult(type, labData, radiologyData)
  }
}
