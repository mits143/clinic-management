package com.clinic.management.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.clinic.management.adapter.LabRadiologyAdapter
import com.clinic.management.adapter.RadiologyAdapter
import com.clinic.management.databinding.FragmentLabRadiologyResultBinding
import com.clinic.management.model.lab.PathologyLabResult
import com.clinic.management.model.radiology.RadiologyLabResult
import com.clinic.management.dailog.DisplayFileDailog
import com.clinic.management.util.setDate

class LabRadiologyResultFragment : BaseFragment<FragmentLabRadiologyResultBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLabRadiologyResultBinding =
        FragmentLabRadiologyResultBinding::inflate

    private val args: LabRadiologyResultFragmentArgs by navArgs()

    private lateinit var dialog: DisplayFileDailog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        if (args.type == 1)
            setLabData()
        else
            setRadiologyData()
    }


    private fun setLabData() {
        binding.txtResult1.setText(args.labData?.lab_name + " -")
        binding.txtResult2.setText(args.labData?.doc_specialization)
        Glide.with(requireContext()).asBitmap().load(args.labData?.lab_logo).into(binding.img)
        binding.txtName.text = args.labData?.lab_name
        binding.txtSpecialist1.text = args.labData?.doc_specialization
        binding.ratingBar.rating = args.labData?.lab_rating!!.toFloat()
        binding.txtDate.setDate(args.labData?.appointment_date!!)
        binding.txtSpecialist.text = args.labData?.doc_specialization
        binding.txtDate1.text = args.labData?.appointment_date
        binding.txtDocPres.text = args.labData?.pathology_note

        val adapter = LabRadiologyAdapter(arrayListOf(), object : LabRadiologyAdapter.OnClick {
            override fun itemClick(data: PathologyLabResult) {
                dialog = DisplayFileDailog(data.pathology_lab_result!!)
                dialog.show(childFragmentManager, "Pathology")
            }
        })
        binding.rvMedicine.adapter = adapter
        adapter.addData(args.labData?.pathology_lab_result!!)
    }


    private fun setRadiologyData() {
        binding.txtResult1.setText(args.radiologyData?.radiology_name + " -")
        binding.txtResult2.setText(args.radiologyData?.doc_specialization)
        Glide.with(requireContext()).asBitmap().load(args.radiologyData?.radiology_logo)
            .into(binding.img)
        binding.txtName.text = args.radiologyData?.radiology_name
        binding.txtSpecialist1.text = args.radiologyData?.doc_specialization
        binding.ratingBar.rating = args.radiologyData?.radiology_rating!!.toFloat()
        binding.txtDate.setDate(args.radiologyData?.appointment_date!!)
        binding.txtSpecialist.text = args.radiologyData?.doc_specialization
        binding.txtDate1.text = args.radiologyData?.appointment_date
        binding.txtDocPres.text = args.radiologyData?.radiology_note

        val adapter = RadiologyAdapter(arrayListOf(), object : RadiologyAdapter.OnClick {
            override fun itemClick(data: RadiologyLabResult) {
                dialog = DisplayFileDailog(data.radiology_lab_result!!)
                dialog.show(childFragmentManager, "Raidology")
            }
        })
        binding.rvMedicine.adapter = adapter
        adapter.addData(args.radiologyData?.radiology_lab_result!!)
    }
}