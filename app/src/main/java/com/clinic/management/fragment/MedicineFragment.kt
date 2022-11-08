package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.clinic.management.adapter.AppointmentAdapter
import com.clinic.management.adapter.MedicineAdapter
import com.clinic.management.databinding.FragmentMedicineBinding
import com.clinic.management.model.medicine.MedicineData
import com.clinic.management.pagination.RecyclerViewLoadMoreScroll
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.MedicineViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class MedicineFragment : BaseFragment<FragmentMedicineBinding>(), MedicineAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMedicineBinding =
        FragmentMedicineBinding::inflate

    private val viewModel: MedicineViewModel by viewModel()

    private lateinit var hud: KProgressHUD
    private var page = 1
    private lateinit var adapter: MedicineAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)

        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        setMedicineData()
    }

    private fun setMedicineData() {
        adapter = MedicineAdapter(arrayListOf(), this)
        binding.rvMedicine.adapter = adapter

        scrollListener = RecyclerViewLoadMoreScroll(binding.rvMedicine.layoutManager!!)
        binding.rvMedicine.addOnScrollListener(scrollListener)
        scrollListener.setOnLoadMoreListener {
            page = page.plus(1)
            viewModel.fetchMedicineData(
                "Bearer " + prefs.accessToken,
                page.toString()
            )
        }
    }

    private fun setObserver() {
        viewModel.fetchMedicineData("Bearer " + prefs.accessToken, page.toString())
        viewModel.getMedicineData.observe(this) {
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
                    }
                }
            }
        }
    }

    override fun itemClick(data: MedicineData) {
        val action =
            MedicineFragmentDirections.actionNavMedicineDetail(data.id)
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