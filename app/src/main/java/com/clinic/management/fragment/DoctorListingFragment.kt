package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.clinic.management.R
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.HomeSpecialistDoctorAdapter
import com.clinic.management.databinding.FragmentDoctorListingBinding
import com.clinic.management.model.home.SpecialistDoctor
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.DoctorListingViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoctorListingFragment : BaseFragment<FragmentDoctorListingBinding>(),
    HomeSpecialistDoctorAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoctorListingBinding =
        FragmentDoctorListingBinding::inflate

    private val viewModel: DoctorListingViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    private val args: DoctorListingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun setDoctorData(list: ArrayList<SpecialistDoctor>) {
        val adapter = HomeSpecialistDoctorAdapter(arrayListOf(), this)
        binding.rvSpecialistDr.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        if (args.isTopDoctor) {
            binding.txtSpecialistDr.text = resources.getString(R.string.top_doctors_in_your_city)
            viewModel.fetchTopDoctorData(
                "Bearer " + prefs.accessToken,
                "18.5074",
                "73.8077",
                "1"
            )
        } else {
            binding.txtSpecialistDr.text = resources.getString(R.string.specialist_doctor)
            viewModel.fetchDoctorData(
                "Bearer " + prefs.accessToken,
                "18.5074",
                "73.8077",
                "1"
            )
        }
        viewModel.getDoctorData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress(true)
                }
                Status.SUCCESS -> {
                    showProgress(false)
                    it.data?.let {
                        setDoctorData(it.data)
                    }
                }
                Status.ERROR -> {
                    showProgress(false)
                    showToast(it.message!!)
                    if (it.message == "Invalid authentication.") {
                        requireActivity().startActivity(
                            Intent(
                                requireContext(),
                                LoginActivity::class.java
                            )
                        )
                        requireActivity().finish()
                        prefs.accessToken = ""
                    }
                }
            }
        }
    }

    override fun itemClick(data: SpecialistDoctor, string: String) {
        if (string == "DOCTOR_DETAIL") {
            val action = HomeFragmentDirections.actionNavDoctorDetail(data.id)
            findNavController().navigate(action)
        } else {
            val action = HomeFragmentDirections.actionNavAppointment(data.id)
            findNavController().navigate(action)
        }
    }

    private fun showProgress(show: Boolean) {
        if (show)
            hud.show()
        else
            if (hud.isShowing)
                hud.dismiss()
    }
}