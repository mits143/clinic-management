package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.clinic.management.R
import com.clinic.management.activities.LoginActivity
import com.clinic.management.databinding.FragmentProfileBinding
import com.clinic.management.prefs

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding =
        FragmentProfileBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(requireContext()).asBitmap().load(prefs.userImage)
            .placeholder(R.drawable.group_3415).into(binding.image)
        binding.txtUsername.text = prefs.userName
        binding.navHome.setOnClickListener {
            findNavController().popBackStack(R.id.nav_profile, true)
            findNavController().navigate(R.id.action_nav_home)
        }
        binding.navMyAppointment.setOnClickListener {
            findNavController().popBackStack(R.id.nav_profile, true)
            findNavController().navigate(R.id.action_nav_my_appointment)
        }
        binding.navAskDoctor.setOnClickListener {
            findNavController().navigate(R.id.action_nav_ask_a_doctor)
        }
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