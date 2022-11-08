package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.clinic.management.R
import com.clinic.management.databinding.FragmentAppointmentConfirmedBinding
import com.clinic.management.prefs

class AppointmentConfirmedFragment : BaseFragment<FragmentAppointmentConfirmedBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAppointmentConfirmedBinding =
        FragmentAppointmentConfirmedBinding::inflate

    private val args: AppointmentConfirmedFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home)
        }
        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.txtAppointment.text = prefs.userName + ", we've got you\nconfirmed appointment"
        binding.txtTime.text = args.time
        binding.txtDrName.text = args.doctorName
        binding.txtLocation1.text = args.date + "\n" + args.address

    }
}