package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.clinic.management.databinding.FragmentMedicineDetailBinding
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.MedicineDetailViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class MedicineDetailFragment : BaseFragment<FragmentMedicineDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMedicineDetailBinding =
        FragmentMedicineDetailBinding::inflate

    private val viewModel: MedicineDetailViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    private val args: MedicineDetailFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)

        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setObserver() {
        viewModel.fetchMedicineDetailData("Bearer " + prefs.accessToken, args.medicineId)
        viewModel.getMedicineDetailData.observe(this) {
            it.getContentIfNotHandled()?.let {
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            binding.txtName.text = it.data.name
                            binding.txtDesc.text = it.data.shortDescription
                        }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                    }
                }
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