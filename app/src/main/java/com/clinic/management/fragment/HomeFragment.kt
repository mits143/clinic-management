package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.R
import com.clinic.management.adapter.MainAdapter
import com.clinic.management.databinding.FragmentHomeBinding
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.CommonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(), MainAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    private val viewModel: CommonViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.btnViewAllSpecialistDr.setOnClickListener {
            findNavController().navigate(R.id.action_nav_doctor_listing)
        }
    }

    private fun setDoctorDummyData(list: ArrayList<String>) {
        val adapter = MainAdapter(arrayListOf(), "Doctor", this)
        binding.rvSpecialistDr.adapter = adapter
        adapter.addData(list)
    }

    private fun setSpecialistDummyData(list: ArrayList<String>) {
        val adapter = MainAdapter(arrayListOf(), "Specialist", this)
        binding.rvSpecialist.adapter = adapter
        adapter.addData(list)
    }

    private fun setTopDoctorDummyData(list: ArrayList<String>) {
        val adapter = MainAdapter(arrayListOf(), "Top Doctor", this)
        binding.rvTopDr.adapter = adapter
        adapter.addData(list)
    }

    private fun setReviewDummyData(list: ArrayList<String>) {
        val adapter = MainAdapter(arrayListOf(), "Review", this)
        binding.rvRecentReview.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchData()
        viewModel.getData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        setDoctorDummyData(it)
                        setSpecialistDummyData(it)
                        setTopDoctorDummyData(it)
                        setReviewDummyData(it)
                    }
                }
                Status.ERROR -> {
                }
            }
        }
    }

    override fun itemClick() {
        findNavController().navigate(R.id.action_nav_doctor_detail)
    }
}