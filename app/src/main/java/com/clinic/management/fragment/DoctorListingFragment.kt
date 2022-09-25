package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.R
import com.clinic.management.adapter.MainAdapter
import com.clinic.management.databinding.FragmentDoctorListingBinding
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.CommonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoctorListingFragment : BaseFragment<FragmentDoctorListingBinding>(), MainAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoctorListingBinding =
        FragmentDoctorListingBinding::inflate

    private val viewModel: CommonViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
    }

    private fun setDoctorDummyData(list: ArrayList<String>) {
        val adapter = MainAdapter(arrayListOf(), "Doctor", this)
        binding.rvSpecialistDr.adapter = adapter
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