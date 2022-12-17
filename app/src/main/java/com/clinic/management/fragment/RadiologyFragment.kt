package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.RadiologyResultAdapter
import com.clinic.management.databinding.FragmentActiveBinding
import com.clinic.management.model.radiology.BookingInformation
import com.clinic.management.model.radiology.RadiologyData
import com.clinic.management.pagination.RecyclerViewLoadMoreScroll
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.Lab_RadiologyViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel


class RadiologyFragment : BaseFragment<FragmentActiveBinding>(), RadiologyResultAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentActiveBinding =
        FragmentActiveBinding::inflate

    private val viewModel: Lab_RadiologyViewModel by viewModel()

    private lateinit var hud: KProgressHUD
    private var pos = 0
    private var page = 1
    private lateinit var adapter: RadiologyResultAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        binding.swipeRefresh.setOnRefreshListener {
            page = 1
            binding.swipeRefresh.isRefreshing = false
            viewModel.fetchLabResultData("Bearer " + prefs.accessToken, page.toString())
        }

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)

        setActiveAppointmentData()
    }

    private fun setActiveAppointmentData() {
        adapter = RadiologyResultAdapter(arrayListOf(), this)
        binding.rvAppointment.adapter = adapter

        scrollListener = RecyclerViewLoadMoreScroll(binding.rvAppointment.layoutManager!!)
        binding.rvAppointment.addOnScrollListener(scrollListener)
        scrollListener.setOnLoadMoreListener {
            page = page.plus(1)
            viewModel.fetchLabResultData(
                "Bearer " + prefs.accessToken,
                page.toString()
            )
        }
    }

    private fun setObserver() {
        viewModel.fetchRadiologyData("Bearer " + prefs.accessToken, page.toString())
        viewModel.getRadiologyData.observe(this) {
            it.getContentIfNotHandled()?.let {
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            if (scrollListener.loaded) {
                                scrollListener.setLoaded()
                            }
                            if (page == 1) {
                                adapter.addData(it.data)
                            } else {
                                adapter.loadMore(it.data)
                            }
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
    }

    override fun itemClick(data: RadiologyData) {
//        pos = position
//        when (type) {
//            "CANCEL" -> {
//                alertBox(
//                    requireContext(),
//                    "Cancel Appointment",
//                    "Are you sure!\nDo you want to cancel this appointment?",
//                    object : Utility.alertClickListener {
//                        override fun clickListener() {
//                            viewModel.fetchCancelAppointmenData(
//                                "Bearer " + prefs.accessToken,
//                                data.id
//                            )
//                        }
//
//                    })
//            }
//        }
    }

    override fun itemChildClick(data: BookingInformation) {
        val action = RadiologyFragmentDirections.actionNavLabRadiologyResult(
            2,
            null,
            data
        )
        findNavController().navigate(action)
    }

    private fun showProgress(show: Boolean) {
        if (show)
            hud.show()
        else
            if (hud.isShowing)
                hud.dismiss()
    }
}