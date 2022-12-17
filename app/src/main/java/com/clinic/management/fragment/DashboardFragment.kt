package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.R
import com.clinic.management.databinding.FragmentDashboardBinding
import com.clinic.management.prefs

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDashboardBinding =
        FragmentDashboardBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.txtName.text = "Hello " + prefs.userName + ", Welcome to"

        binding.llHome.setOnClickListener {
            val action = DashboardFragmentDirections.actionNavHome()
            findNavController().navigate(action)
        }
        binding.llAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_nav_my_appointment)
        }
        binding.llAskDoctor.setOnClickListener {
            findNavController().navigate(R.id.action_nav_ask_a_doctor)
        }
        binding.llMyResult.setOnClickListener {
            val action = DashboardFragmentDirections.actionNavMyResult(0)
            findNavController().navigate(action)

        }
        binding.flMedicine.setOnClickListener {
            findNavController().navigate(R.id.action_nav_medicine)
        }
    }
}