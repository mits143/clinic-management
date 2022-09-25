package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.R
import com.clinic.management.databinding.FragmentAppointmentBinding

class AppointmentFragment : BaseFragment<FragmentAppointmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAppointmentBinding =
        FragmentAppointmentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_nav_appointment_confirmed)
        }
    }
}