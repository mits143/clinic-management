package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.clinic.management.R
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.HomeSpecialistAdapter
import com.clinic.management.databinding.FragmentDoctorListingBinding
import com.clinic.management.model.home.SpecialCategory
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.CategoryListingViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryListingFragment : BaseFragment<FragmentDoctorListingBinding>(),
    HomeSpecialistAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoctorListingBinding =
        FragmentDoctorListingBinding::inflate

    private val viewModel: CategoryListingViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.txtSpecialistDr.text = resources.getString(R.string.specialities)
        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun setSpecialistData(list: ArrayList<SpecialCategory>) {
        binding.rvSpecialistDr.layoutManager = GridLayoutManager(requireContext(), 3)
        val adapter = HomeSpecialistAdapter(arrayListOf(), this)
        binding.rvSpecialistDr.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchCategoryData(
            "Bearer " + prefs.accessToken,
            "18.5074",
            "73.8077"
        )
        viewModel.getCategoryData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            setSpecialistData(it.data)
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

    override fun itemClick(data: SpecialCategory) {
        val action =
            CategoryListingFragmentDirections.actionNavDoctorListing(false, data.id)
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