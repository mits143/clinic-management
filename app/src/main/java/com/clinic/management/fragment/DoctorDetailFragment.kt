package com.clinic.management.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.clinic.management.activities.LoginActivity
import com.clinic.management.databinding.FragmentDoctorDetailBinding
import com.clinic.management.model.doctor.DoctorDetailData
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.DoctorDetailViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DoctorDetailFragment : BaseFragment<FragmentDoctorDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoctorDetailBinding =
        FragmentDoctorDetailBinding::inflate

    private val args: DoctorDetailFragmentArgs by navArgs()

    private val viewModel: DoctorDetailViewModel by viewModel { parametersOf(args.doctorId) }

    private lateinit var hud: KProgressHUD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        binding.txtLocation.setText(prefs.city)

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    //
    private fun setDoctorDetailData(data: DoctorDetailData) {
        binding.txtExp.text = data.totalYearExp
        binding.txtName.text = data.docName
        binding.txtSpecialist.text = data.degree
        binding.txtSpecialist1.text = data.specialization
        binding.txtLocation1.text = data.latitude + data.longitude
        binding.btnViews.text = data.avgRating

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            binding.txtDesc.text = Html.fromHtml(data.description, Html.FROM_HTML_MODE_LEGACY)
        else
            binding.txtDesc.text = Html.fromHtml(data.description);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            binding.txtEducation.text =
                Html.fromHtml(data.educationInformation, Html.FROM_HTML_MODE_LEGACY)
        else
            binding.txtEducation.text = Html.fromHtml(data.educationInformation);

        binding.btnViewAllSpecialist.setOnClickListener {
            val action = DoctorDetailFragmentDirections.actionNavAppointment(
                data.id,
                data.docName,
                data.degree,
                data.specialization,
                "0"
            )
            findNavController().navigate(action)
        }
    }

    private fun setObserver() {
        viewModel.fetchDoctorDetailData(
            "Bearer " + prefs.accessToken, prefs.latitude!!, prefs.longitude!!, args.doctorId
        )
        viewModel.getDoctorDetailData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            setDoctorDetailData(it.data[0])
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

    private fun showProgress(show: Boolean) {
        if (show)
            hud.show()
        else
            if (hud.isShowing)
                hud.dismiss()
    }
}