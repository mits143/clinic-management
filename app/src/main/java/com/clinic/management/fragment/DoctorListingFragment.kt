package com.clinic.management.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.clinic.management.R
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.HomeSpecialistDoctorAdapter
import com.clinic.management.databinding.FragmentDoctorListingBinding
import com.clinic.management.model.home.SpecialistDoctor
import com.clinic.management.pagination.RecyclerViewLoadMoreScroll
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.DoctorListingViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoctorListingFragment : BaseFragment<FragmentDoctorListingBinding>(),
    HomeSpecialistDoctorAdapter.OnClick, SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoctorListingBinding =
        FragmentDoctorListingBinding::inflate

    private val viewModel: DoctorListingViewModel by viewModel()

    private lateinit var hud: KProgressHUD
    private var searchText = ""
    private var page = 1
    private lateinit var adapter: HomeSpecialistDoctorAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

    private val args: DoctorListingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        binding.txtLocation.setText(prefs.city)
        binding.searchView.setOnQueryTextListener(this)
        binding.btnFilter.visibility = View.GONE

        binding.swipeRefresh.setOnRefreshListener {
            page = 1
            searchText = ""
            binding.searchView.setQuery("", false);
            binding.searchView.clearFocus()
            binding.swipeRefresh.isRefreshing = false
            if (args.categoryID == "0") {
                binding.txtSpecialistDr.text = resources.getString(R.string.doctor_listing)
                viewModel.fetchSearchResultData(
                    "Bearer " + prefs.accessToken,
                    prefs.latitude!!,
                    prefs.longitude!!,
                    searchText,
                    "",
                    "",
                    "0",
                    page.toString()
                )
            } else if (args.categoryID == "-1") {
                if (args.isTopDoctor) {
                    binding.txtSpecialistDr.text =
                        resources.getString(R.string.top_doctors_in_your_city)
                    viewModel.fetchTopDoctorData(
                        "Bearer " + prefs.accessToken,
                        prefs.latitude!!,
                        prefs.longitude!!,
                        page.toString()
                    )
                } else {
                    binding.txtSpecialistDr.text = resources.getString(R.string.specialist_doctor)
                    viewModel.fetchDoctorData(
                        "Bearer " + prefs.accessToken,
                        prefs.latitude!!,
                        prefs.longitude!!,
                        page.toString()
                    )
                }
            } else {
                binding.txtSpecialistDr.text = resources.getString(R.string.specialist_doctor)
                viewModel.fetchCategoryDoctorData(
                    "Bearer " + prefs.accessToken,
                    args.categoryID,
                    prefs.latitude!!,
                    prefs.longitude!!,
                    page.toString()
                )

            }
        }

        hud = KProgressHUD.create(requireContext()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)

        setDoctorData()
    }

    private fun setDoctorData() {
        adapter = HomeSpecialistDoctorAdapter(arrayListOf(), this)
        binding.rvSpecialistDr.adapter = adapter

        scrollListener = RecyclerViewLoadMoreScroll(binding.rvSpecialistDr.layoutManager!!)
        binding.rvSpecialistDr.addOnScrollListener(scrollListener)
        scrollListener.setOnLoadMoreListener {
            page = page.plus(1)
            if (args.categoryID == "0") {
                binding.txtSpecialistDr.text = resources.getString(R.string.doctor_listing)
                viewModel.fetchSearchResultData(
                    "Bearer " + prefs.accessToken,
                    prefs.latitude!!,
                    prefs.longitude!!,
                    searchText,
                    "",
                    "",
                    "0",
                    page.toString()
                )
            } else if (args.categoryID == "-1") {
                if (args.isTopDoctor) {
                    binding.txtSpecialistDr.text =
                        resources.getString(R.string.top_doctors_in_your_city)
                    viewModel.fetchTopDoctorData(
                        "Bearer " + prefs.accessToken,
                        prefs.latitude!!,
                        prefs.longitude!!,
                        page.toString()
                    )
                } else {
                    binding.txtSpecialistDr.text = resources.getString(R.string.specialist_doctor)
                    viewModel.fetchDoctorData(
                        "Bearer " + prefs.accessToken,
                        prefs.latitude!!,
                        prefs.longitude!!,
                        page.toString()
                    )
                }
            } else {
                binding.txtSpecialistDr.text = resources.getString(R.string.specialist_doctor)
                viewModel.fetchCategoryDoctorData(
                    "Bearer " + prefs.accessToken,
                    args.categoryID,
                    prefs.latitude!!,
                    prefs.longitude!!,
                    page.toString()
                )
            }
        }
    }

    private fun setObserver() {
        if (args.categoryID == "0") {
            binding.txtSpecialistDr.text = resources.getString(R.string.doctor_listing)
            viewModel.fetchSearchResultData(
                "Bearer " + prefs.accessToken,
                prefs.latitude!!,
                prefs.longitude!!,
                searchText,
                "",
                "",
                "",
                page.toString()
            )
        } else if (args.categoryID == "-1") {
            if (args.isTopDoctor) {
                binding.txtSpecialistDr.text =
                    resources.getString(R.string.top_doctors_in_your_city)
                viewModel.fetchTopDoctorData(
                    "Bearer " + prefs.accessToken,
                    prefs.latitude!!,
                    prefs.longitude!!,
                    page.toString()
                )
            } else {
                binding.txtSpecialistDr.text = resources.getString(R.string.specialist_doctor)
                viewModel.fetchDoctorData(
                    "Bearer " + prefs.accessToken,
                    prefs.latitude!!,
                    prefs.longitude!!,
                    page.toString()
                )
            }
        } else {
            binding.txtSpecialistDr.text = resources.getString(R.string.specialist_doctor)
            viewModel.fetchCategoryDoctorData(
                "Bearer " + prefs.accessToken,
                args.categoryID,
                prefs.latitude!!,
                prefs.longitude!!,
                page.toString()
            )
        }
        viewModel.getDoctorData.observe(this) {
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
                                    requireContext(), LoginActivity::class.java
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
            val action = DoctorListingFragmentDirections.actionNavDoctorDetail(data.id)
            findNavController().navigate(action)
        } else {
            val action = DoctorListingFragmentDirections.actionNavAppointment(
                data.id, data.docName, data.degree, data.specialization, "0"
            )
            findNavController().navigate(action)
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) hud.show()
        else if (hud.isShowing) hud.dismiss()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        page = 1
        if (newText!!.isNotEmpty()) {
            searchText = newText!!
            viewModel.fetchSearchResultData(
                "Bearer " + prefs.accessToken,
                prefs.latitude!!,
                prefs.longitude!!,
                searchText,
                "",
                "",
                "0",
                page.toString()
            )
        }
        return false
    }
}