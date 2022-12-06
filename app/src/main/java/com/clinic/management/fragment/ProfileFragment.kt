package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.R
import com.clinic.management.activities.LoginActivity
import com.clinic.management.databinding.FragmentProfileBinding
import com.clinic.management.prefs

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding =
        FragmentProfileBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.navHome.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home)
        }
        binding.navMyAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_nav_my_appointment)
        }
        binding.navAskDoctor.setOnClickListener {
            findNavController().navigate(R.id.action_nav_ask_a_doctor)
        }
//        binding.navDoctorResult.setOnClickListener {
//            val action = ProfileFragmentDirections.actionNavMyResult(0)
//            findNavController().navigate(action)
//        }
        binding.navLabResult.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavMyResult(0)
            findNavController().navigate(action)
        }
        binding.navRadiologyResult.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavMyResult(1)
            findNavController().navigate(action)
        }
        binding.navLogout.setOnClickListener {
            prefs.accessToken = ""
            requireActivity().finish()
            context?.startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }
}