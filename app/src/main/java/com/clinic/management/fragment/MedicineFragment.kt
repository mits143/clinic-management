package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clinic.management.databinding.FragmentMedicineBinding

class MedicineFragment : BaseFragment<FragmentMedicineBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMedicineBinding =
        FragmentMedicineBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}