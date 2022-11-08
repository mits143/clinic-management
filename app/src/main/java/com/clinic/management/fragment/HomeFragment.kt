package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.HomeReviewsAdapter
import com.clinic.management.adapter.HomeSpecialistAdapter
import com.clinic.management.adapter.HomeSpecialistDoctorAdapter
import com.clinic.management.databinding.FragmentHomeBinding
import com.clinic.management.model.home.ReviewsListing
import com.clinic.management.model.home.SpecialCategory
import com.clinic.management.model.home.SpecialistDoctor
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.HomeViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeSpecialistDoctorAdapter.OnClick,
    HomeSpecialistAdapter.OnClick, HomeReviewsAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate


    private val viewModel: HomeViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.btnViewAllSpecialistDr.setOnClickListener {
            val action = HomeFragmentDirections.actionNavDoctorListing(false, "0")
            findNavController().navigate(action)
        }
        binding.btnViewAllSpecialist.setOnClickListener {
            val action = HomeFragmentDirections.actionNavCategory()
            findNavController().navigate(action)
        }
        binding.btnViewAllTopDr.setOnClickListener {
            val action = HomeFragmentDirections.actionNavDoctorListing(true, "0")
            findNavController().navigate(action)
        }
        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun setDoctorData(list: ArrayList<SpecialistDoctor>) {
        val adapter = HomeSpecialistDoctorAdapter(arrayListOf(), this)
        binding.rvSpecialistDr.adapter = adapter
        adapter.addData(list)
    }

    private fun setSpecialistData(list: ArrayList<SpecialCategory>) {
        val adapter = HomeSpecialistAdapter(arrayListOf(), this)
        binding.rvSpecialist.adapter = adapter
        adapter.addData(list)
    }

    //
    private fun setTopDoctorData(list: ArrayList<SpecialistDoctor>) {
        val adapter = HomeSpecialistDoctorAdapter(arrayListOf(), this)
        binding.rvTopDr.adapter = adapter
        adapter.addData(list)
    }

    private fun setReviewData(list: ArrayList<ReviewsListing>) {
        val adapter = HomeReviewsAdapter(arrayListOf(), this)
        binding.rvRecentReview.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchHomeData(
            "Bearer " + prefs.accessToken, "18.5074", "73.8077"
        )
        viewModel.getHomeData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        it.data?.let {
                            showProgress(false)
                            setDoctorData(it.specialistDoctor)
                            setSpecialistData(it.specialCategory)
                            setTopDoctorData(it.topDoctors)
                            setReviewData(it.reviewsListing)
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

    override fun itemClick(data: SpecialistDoctor, string: String) {
        if (string == "DOCTOR_DETAIL") {
            val action = HomeFragmentDirections.actionNavDoctorDetail(data.id)
            findNavController().navigate(action)
        } else {
            val action = HomeFragmentDirections.actionNavAppointment(data.id, "0")
            findNavController().navigate(action)
        }
    }

    override fun itemClick(data: SpecialCategory) {
        val action = HomeFragmentDirections.actionNavDoctorListing(false, data.id)
        findNavController().navigate(action)
    }

    override fun itemClick(data: ReviewsListing) {
    }

    private fun showProgress(show: Boolean) {
        if (show)
            hud.show()
        else
            hud.dismiss()
    }
}