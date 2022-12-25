package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.AppointmentAdapter
import com.clinic.management.dailog.QueueDetailDailog
import com.clinic.management.databinding.FragmentActiveBinding
import com.clinic.management.model.appointmments.ActiveAppointmentData
import com.clinic.management.pagination.RecyclerViewLoadMoreScroll
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.util.Utility
import com.clinic.management.util.Utility.alertBox
import com.clinic.management.viewmodel.ActiveAppointmentViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel


class ActiveFragment : BaseFragment<FragmentActiveBinding>(), AppointmentAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentActiveBinding =
        FragmentActiveBinding::inflate

    private val viewModel: ActiveAppointmentViewModel by viewModel()

    private lateinit var hud: KProgressHUD
    private var pos = 0
    private var page = 1
    private lateinit var adapter: AppointmentAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

    private lateinit var queueDetailDailog: QueueDetailDailog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        binding.swipeRefresh.setOnRefreshListener {
            page = 1
            binding.swipeRefresh.isRefreshing = false
            viewModel.fetchActiveAppointmentData("Bearer " + prefs.accessToken, page.toString())
        }

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)

        setActiveAppointmentData()
    }

    private fun setActiveAppointmentData() {
        adapter = AppointmentAdapter(arrayListOf(), this, true)
        binding.rvAppointment.adapter = adapter

        scrollListener = RecyclerViewLoadMoreScroll(binding.rvAppointment.layoutManager!!)
        binding.rvAppointment.addOnScrollListener(scrollListener)
        scrollListener.setOnLoadMoreListener {
            page = page.plus(1)
            viewModel.fetchActiveAppointmentData(
                "Bearer " + prefs.accessToken,
                page.toString()
            )
        }
    }

    private fun setObserver() {
        viewModel.fetchActiveAppointmentData("Bearer " + prefs.accessToken, page.toString())
        viewModel.getAppointmentData.observe(this) {
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
        viewModel.getAppointmenCanceltData.observe(this) {
            it.getContentIfNotHandled()?.let {
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            showToast(it["message"].asString)
                            adapter.removeItem(pos)
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

    override fun itemClick(data: ActiveAppointmentData, type: String, position: Int) {
        pos = position
        when (type) {
            "CANCEL" -> {
                alertBox(
                    requireContext(),
                    "Cancel Appointment",
                    "Are you sure!\nDo you want to cancel this appointment?",
                    object : Utility.alertClickListener {
                        override fun clickListener() {
                            viewModel.fetchCancelAppointmenData(
                                "Bearer " + prefs.accessToken,
                                data.id
                            )
                        }

                    })
            }
            "RESCHEDULE" -> {
                alertBox(
                    requireContext(),
                    "Reschedule Appointment",
                    "Are you sure!\nDo you want to reschedule this appointment?",
                    object : Utility.alertClickListener {
                        override fun clickListener() {
                            val action = ActiveFragmentDirections.actionNavAppointment(
                                data.doctorId,
                                data.docName,
                                "",
                                "",
                                data.id
                            )
                            findNavController().navigate(action)
                        }
                    })
            }
            "POSITIONLIST" -> {
                queueDetailDailog = QueueDetailDailog(data.position_list)
                queueDetailDailog.show(childFragmentManager, "Queue")

            }
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