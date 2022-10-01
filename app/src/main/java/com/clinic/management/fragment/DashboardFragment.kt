package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.R
import com.clinic.management.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDashboardBinding =
        FragmentDashboardBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.llAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_nav_appointment)
        }
        binding.llAskDoctor.setOnClickListener {
            findNavController().navigate(R.id.action_nav_appointment)
        }
        binding.llDoctorResult.setOnClickListener {
            val action = DashboardFragmentDirections.actionNavMyResult(0)
            findNavController().navigate(action)

        }
        binding.llLabResult.setOnClickListener {
            val action = DashboardFragmentDirections.actionNavMyResult(1)
            findNavController().navigate(action)

        }
        binding.flMedicine.setOnClickListener {
            findNavController().navigate(R.id.action_nav_medicine)
        }
    }
}