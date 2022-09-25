package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clinic.management.databinding.FragmentDoctorDetailBinding

class DoctorDetailFragment : BaseFragment<FragmentDoctorDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoctorDetailBinding =
        FragmentDoctorDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}