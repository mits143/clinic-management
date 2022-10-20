package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.R
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.AppointmentAdapter
import com.clinic.management.databinding.FragmentActiveBinding
import com.clinic.management.model.appointmments.AppointmentData
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.AppointmentViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaqFragment : BaseFragment<FragmentActiveBinding>(), AppointmentAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentActiveBinding =
        FragmentActiveBinding::inflate

    private val viewModel: AppointmentViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun setActiveAppointmentData(list: ArrayList<AppointmentData>) {
        val adapter = AppointmentAdapter(arrayListOf(), this, true)
        binding.rvAppointment.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchActiveAppointmentData("Bearer " + prefs.accessToken, "1")
        viewModel.getAppointmentData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress(true)
                }
                Status.SUCCESS -> {
                    showProgress(false)
                    it.data?.let {
                        setActiveAppointmentData(it.data)
                    }
                }
                Status.ERROR -> {
                    showProgress(false)
                    showToast(it.message!!)
                    if (it.message == "Invalid authentication.") {
                        requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
                        requireActivity().finish()
                        prefs.accessToken = ""
                    }
                }
            }
        }
    }

    override fun itemClick(data: AppointmentData) {
    }

    private fun showProgress(show: Boolean) {
        if (show)
            hud.show()
        else
            if (hud.isShowing)
            hud.dismiss()
    }
}