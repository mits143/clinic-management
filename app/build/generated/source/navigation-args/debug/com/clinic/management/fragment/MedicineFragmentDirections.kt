package com.clinic.management.fragment

import android.os.Bundle
import androidx.navigation.NavDirections
import com.clinic.management.R
import kotlin.Int
import kotlin.String

public class MedicineFragmentDirections private constructor() {
  private data class ActionNavMedicineDetail(
    public val medicineId: String = "0"
  ) : NavDirections {
    public override val actionId: Int = R.id.action_nav_medicine_detail

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("medicine_id", this.medicineId)
        return result
      }
  }

  public companion object {
    public fun actionNavMedicineDetail(medicineId: String = "0"): NavDirections =
        ActionNavMedicineDetail(medicineId)
  }
}
