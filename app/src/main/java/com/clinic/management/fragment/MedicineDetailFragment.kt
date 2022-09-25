package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clinic.management.databinding.FragmentMedicineDetailBinding

class MedicineDetailFragment : BaseFragment<FragmentMedicineDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMedicineDetailBinding =
        FragmentMedicineDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}