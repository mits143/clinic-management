package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.clinic.management.adapter.FilterAdapter
import com.clinic.management.adapter.SpecialistAdapter
import com.clinic.management.databinding.FragmentFilterBinding
import com.clinic.management.fragment.SearchFragment.Companion.distance
import com.clinic.management.fragment.SearchFragment.Companion.list
import com.clinic.management.fragment.SearchFragment.Companion.rating
import com.clinic.management.model.home.SpecialCategory
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.FilterViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterFragment : BaseFragment<FragmentFilterBinding>(), FilterAdapter.OnClick,
    SpecialistAdapter.OnClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFilterBinding =
        FragmentFilterBinding::inflate

    private val viewModel: FilterViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    private lateinit var adapter: SpecialistAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setObserver()
        setRatingSeekBar()
        setDistanceSeekBar()

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)

        binding.btnClear.setOnClickListener {
            adapter.clearSelectedItems()
            binding.txtSeekEnd.text = "0"
            binding.seekBarRating.progress = 0
            binding.txtSeekDistanceEnd.text = "0"
            binding.seekBarDistance.progress = 0
            list = adapter.getSelectedItem()
            rating = binding.seekBarRating.progress.toString()
            distance = binding.seekBarDistance.progress.toString()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnConfirm.setOnClickListener {
            list = adapter.getSelectedItem()
            rating = binding.txtSeekEnd.text.toString().trim()
            distance = binding.txtSeekDistanceEnd.text.toString().trim()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun setFilterData(list: ArrayList<String>) {
        val adapter = FilterAdapter(arrayListOf(), this)
        binding.rvFilter.adapter = adapter
        adapter.addData(list)
    }

    private fun setSpecializationData(list: ArrayList<SpecialCategory>) {
        adapter = SpecialistAdapter(arrayListOf(), this)
        binding.rvFilterList.adapter = adapter
        adapter.addData(list)
    }

    fun setRatingSeekBar() {
        binding.seekBarRating.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {
                binding.txtSeekEnd.text = seek.progress.toString()
                if (adapter.getSelectedItem()
                        .isNotEmpty() || binding.seekBarRating.progress > 0 || binding.seekBarDistance.progress > 0
                )
                    binding.btnClear.visibility = View.VISIBLE
                else
                    binding.btnClear.visibility = View.GONE
            }
        })
    }

    fun setDistanceSeekBar() {
        binding.seekBarDistance.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {
                binding.txtSeekDistanceEnd.text = seek.progress.toString()
                if (adapter.getSelectedItem()
                        .isNotEmpty() || binding.seekBarRating.progress > 0 || binding.seekBarDistance.progress > 0
                )
                    binding.btnClear.visibility = View.VISIBLE
                else
                    binding.btnClear.visibility = View.GONE
            }
        })
    }

    private fun setObserver() {
        viewModel.fetchFilterList()
        viewModel.getData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        it.data?.let {
                            setFilterData(it)
                        }
                    }
                    Status.ERROR -> {
                    }
                }
            }
        }
        viewModel.fetchCategoryData(
            "Bearer " + prefs.accessToken,
            prefs.latitude!!,
            prefs.longitude!!
        )
        viewModel.getCategoryData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        it.data?.let {
                            setSpecializationData(it.data)
                            if (list.isEmpty() && rating == "0" && distance == "0") {
                                binding.btnClear.visibility = View.GONE
                            } else {
                                binding.btnClear.visibility = View.VISIBLE
                                if (list.isNotEmpty()) {
                                    val result = list.split(",").map { it.trim() }
                                    adapter.lastSelectedItem(result)
                                }
                                binding.txtSeekEnd.text = rating
                                binding.seekBarRating.progress = rating.toInt()
                                binding.txtSeekDistanceEnd.text = distance
                                binding.seekBarDistance.progress = distance.toInt()

                            }
                        }
                    }
                    Status.ERROR -> {
                    }
                }
            }
        }
    }

    override fun itemClick(data: String) {
        if (data == "By Speciality") {
            binding.rvFilterList.visibility = View.VISIBLE
            binding.clSeekBar.visibility = View.GONE
            binding.clDistanceSeekBar.visibility = View.GONE
        } else if (data == "By Rating") {
            binding.rvFilterList.visibility = View.GONE
            binding.clSeekBar.visibility = View.VISIBLE
            binding.clDistanceSeekBar.visibility = View.GONE
        } else {
            binding.rvFilterList.visibility = View.GONE
            binding.clSeekBar.visibility = View.GONE
            binding.clDistanceSeekBar.visibility = View.VISIBLE
        }

    }

    private fun showProgress(show: Boolean) {
        if (show)
            hud.show()
        else
            if (hud.isShowing)
                hud.dismiss()
    }

    override fun itemClick(data: SpecialCategory) {
        data.isChecked = !data.isChecked
        if (adapter.getSelectedItem()
                .isNotEmpty() || binding.seekBarRating.progress > 0 || binding.seekBarDistance.progress > 0
        )
            binding.btnClear.visibility = View.VISIBLE
        else
            binding.btnClear.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }
}