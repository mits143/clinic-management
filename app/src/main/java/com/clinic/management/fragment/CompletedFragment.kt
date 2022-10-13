package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.CompletedAppointmentAdapter
import com.clinic.management.databinding.FragmentActiveBinding
import com.clinic.management.model.appointmments.CompleteAppointmentData
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.CompleteAppointmentViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompletedFragment : BaseFragment<FragmentActiveBinding>(),
    CompletedAppointmentAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentActiveBinding =
        FragmentActiveBinding::inflate

    private val viewModel: CompleteAppointmentViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun setCompletedAppointmentData(list: ArrayList<CompleteAppointmentData>) {
        val adapter = CompletedAppointmentAdapter(arrayListOf(), this)
        binding.rvAppointment.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchCompleteAppointmentData("Bearer " + prefs.accessToken, "1")
        viewModel.getCompleteAppointmentData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress(true)
                }
                Status.SUCCESS -> {
                    showProgress(false)
                    it.data?.let {
                        setCompletedAppointmentData(it.data)
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

    override fun itemClick(data: CompleteAppointmentData) {
    }

    private fun showProgress(show: Boolean) {
        if (show)
            hud.show()
        else
            hud.dismiss()
    }
}