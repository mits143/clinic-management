package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.CompletedAppointmentAdapter
import com.clinic.management.databinding.FragmentActiveBinding
import com.clinic.management.model.appointmments.CompleteAppointmentData
import com.clinic.management.pagination.RecyclerViewLoadMoreScroll
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
    private var page = 1
    private lateinit var adapter: CompletedAppointmentAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        binding.swipeRefresh.setOnRefreshListener {
            page = 1
            binding.swipeRefresh.isRefreshing = false
            viewModel.fetchCompleteAppointmentData("Bearer " + prefs.accessToken, page.toString())
        }

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)

        setCompletedAppointmentData()
    }

    private fun setCompletedAppointmentData() {
        adapter = CompletedAppointmentAdapter(arrayListOf(), this)
        binding.rvAppointment.adapter = adapter

        scrollListener = RecyclerViewLoadMoreScroll(binding.rvAppointment.layoutManager!!)
        binding.rvAppointment.addOnScrollListener(scrollListener)
        scrollListener.setOnLoadMoreListener {
            page = page.plus(1)
            viewModel.fetchCompleteAppointmentData(
                "Bearer " + prefs.accessToken,
                page.toString()
            )
        }
    }

    private fun setObserver() {
        viewModel.fetchCompleteAppointmentData("Bearer " + prefs.accessToken, page.toString())
        viewModel.getCompleteAppointmentData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
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

    override fun itemClick(data: CompleteAppointmentData) {
        val action = CompletedFragmentDirections.actionNavDoctorAppointment(
            "9"
        )
        findNavController().navigate(action)
    }

    private fun showProgress(show: Boolean) {
        if (show)
            hud.show()
        else
            hud.dismiss()
    }
}