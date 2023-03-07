package com.clinic.management.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.clinic.management.activities.LoginActivity
import com.clinic.management.adapter.ImagesAdapter
import com.clinic.management.adapter.MedicinePrescriptionAdapter
import com.clinic.management.adapter.PathologyAdapter
import com.clinic.management.adapter.RadiologyAdapter1
import com.clinic.management.dailog.DisplayFileDailog
import com.clinic.management.dailog.RateDoctorDailog
import com.clinic.management.dailog.UploadDailog
import com.clinic.management.databinding.FragmentDoctorResultBinding
import com.clinic.management.model.doctorResult.PathologyLabResult
import com.clinic.management.model.doctorResult.PrescriptionMedicine
import com.clinic.management.model.doctorResult.RadiologyLabResult
import com.clinic.management.prefs
import com.clinic.management.util.Callback
import com.clinic.management.util.Status
import com.clinic.management.util.Utility.prepareFilePart
import com.clinic.management.util.setDate
import com.clinic.management.util.setHtmlText
import com.clinic.management.viewmodel.DoctorAppointmentViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoctorResultFragment : BaseFragment<FragmentDoctorResultBinding>(),
    UploadDailog.onButtonClick, RateDoctorDailog.onButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoctorResultBinding =
        FragmentDoctorResultBinding::inflate

    private val args: DoctorResultFragmentArgs by navArgs()

    private val viewModel: DoctorAppointmentViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    private lateinit var uploaddailog: UploadDailog
    private lateinit var ratedoctordailog: RateDoctorDailog

    var files: ArrayList<Uri> = arrayListOf()

    var parts: ArrayList<MultipartBody.Part> = arrayListOf()

    private lateinit var imagesAdapter: ImagesAdapter

    private var isPathology = false

    private lateinit var dialog: DisplayFileDailog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        callbacks()

        uploaddailog = UploadDailog(this)
        ratedoctordailog = RateDoctorDailog(this)

        imagesAdapter = ImagesAdapter(files)

        hud = KProgressHUD.create(requireContext()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)

        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnUploadFile.setOnClickListener {
            isPathology = true
            uploaddailog.show(childFragmentManager, "PathologyDialog")
        }
        binding.btnUploadFile1.setOnClickListener {
            isPathology = false
            uploaddailog.show(childFragmentManager, "RadiologyDialog")
        }
        binding.btnRateDr.setOnClickListener {
            ratedoctordailog.show(childFragmentManager, "RateDoctor")
        }
    }

    private fun setMedicineData(list: ArrayList<PrescriptionMedicine>) {
        val adapter = MedicinePrescriptionAdapter(arrayListOf())
        binding.rvMedicine.adapter = adapter
        adapter.addData(list)
    }

    private fun setPathologyData(list: ArrayList<PathologyLabResult>) {
        val adapter = PathologyAdapter(arrayListOf(), object : PathologyAdapter.OnClick {
            override fun itemClick(data: PathologyLabResult) {
                dialog = DisplayFileDailog(data.file)
                dialog.show(childFragmentManager, "Pathology")
            }
        })
        binding.rvPathologyImages.adapter = adapter
        adapter.addData(list)
    }

    private fun setRadiologyData(list: ArrayList<RadiologyLabResult>) {
        val adapter = RadiologyAdapter1(arrayListOf(), object : RadiologyAdapter1.OnClick {
            override fun itemClick(data: RadiologyLabResult) {
                dialog = DisplayFileDailog(data.file)
                dialog.show(childFragmentManager, "Radiology")
            }

        })
        binding.rvRadiologyImages.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchAppointmentData("Bearer " + prefs.accessToken, args.id)
        viewModel.getAppointmentData.observe(this) {
            it.getContentIfNotHandled()?.let {
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            binding.txtResult1.setText(it.data.doc_name + " -")
                            binding.txtResult2.setText(it.data.doc_specialization)
                            Glide.with(requireContext()).asBitmap().load(it.data.doc_image)
                                .into(binding.img)
                            binding.txtName.text = it.data.doc_name
                            binding.txtSpecialist1.text = it.data.doc_specialization
                            binding.ratingBar.rating = it.data.doc_avg_rating.toFloat()
                            binding.txtDate.setDate(it.data.appointment_date)
                            binding.txtSpecialist.text = it.data.doc_specialization
                            binding.txtDate1.text = it.data.appointment_date
                            binding.txtDocPres.setHtmlText(it.data.doc_prescription_note)
                            setMedicineData(it.data.prescription_medicine)
                            if (it.data.pathology != null) {
                                Glide.with(requireContext()).asBitmap().load(it.data.pathology.logo)
                                    .into(binding.imgLogo)
                                binding.txtTitle.text = it.data.pathology.name
                                binding.txtDesc.text = it.data.pathology.pathology_note_from_doctor
                                setPathologyData(it.data.pathology_lab_result)
                            } else {
                                binding.cvPathology.visibility = View.GONE
                            }
                            if (it.data.radiology != null) {
                                Glide.with(requireContext()).asBitmap().load(it.data.radiology.logo)
                                    .into(binding.imgLogo1)
                                binding.txtTitle1.text = it.data.radiology.name
                                binding.txtDesc1.text = it.data.radiology.radiology_note_from_doctor
                                setRadiologyData(it.data.radiology_lab_result)
                            } else {
                                binding.cvRadiology.visibility = View.GONE
                            }
                            if (it.data.is_review.equals("yes"))
                                binding.btnRateDr.visibility = View.GONE
                            else
                                binding.btnRateDr.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                    }
                }
            }
        }
        viewModel.getUpload.observe(this) {
            it.getContentIfNotHandled()?.let {
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            showToast(it["message"].asString)
                        }
                        viewModel.fetchAppointmentData("Bearer " + prefs.accessToken, args.id)
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
        viewModel.getRateDoctor.observe(this) {
            it.getContentIfNotHandled()?.let {
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            showToast(it["message"].asString)
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
        if (show) hud.show()
        else if (hud.isShowing) hud.dismiss()
    }

    override fun onClickSelectFile() {
        getPermission()
    }

    private fun callbacks() {
        setUpListener(object : Callback {
            override fun captureImageData(data: Intent?) {
//                val path = getPath(requireContext(), data!!)
//                file = File(path!!)
//                binding.edtDoc.setText(file?.name.toString().trim())
            }

            override fun browseImageData(data: Intent?) {
                if (data!!.clipData != null) {
                    val count: Int = data.clipData!!.itemCount
                    var currentItem = 0
                    while (currentItem < count) {
                        val imageUri: Uri = data.clipData!!.getItemAt(currentItem).uri
                        currentItem += 1
                        try {
                            files.add(imageUri)
                        } catch (e: Exception) {
                            e.stackTrace
                        }
                    }
                } else if (data.data != null) {
                    try {
                        files.add(data.data!!)
                    } catch (e: java.lang.Exception) {
                        e.stackTrace
                    }
                }
                uploaddailog.bindImages(imagesAdapter)
            }

            override fun pdfData(data: Intent?) {
            }

            override fun permissionGranted() {
                selectImage()
            }

            override fun permissionNotGranted() {
            }

        })
    }

    override fun onClickUploadFile(title: String, desc: String) {
        if (files.isNotEmpty()) {
            val appoint_id = args.id.toRequestBody(MultipartBody.FORM)
            val note = desc.toRequestBody(MultipartBody.FORM)

            if (files.isNotEmpty()) {
                parts.clear()
                for (i in files.indices) {
                    if (isPathology) parts.add(
                        prepareFilePart(
                            requireActivity(), "pathology_lab_result[]", files[i]
                        )!!
                    )
                    else parts.add(
                        prepareFilePart(
                            requireActivity(), "radiology_lab_result[]", files[i]
                        )!!
                    )
                }
            }
            if (isPathology) viewModel.fetchUploadPathologyData(
                "Bearer " + prefs.accessToken, appoint_id, note, parts
            )
            else viewModel.fetchUploadRadiologyData(
                "Bearer " + prefs.accessToken, appoint_id, note, parts
            )
        }
        files.clear()
    }

    override fun onClickSubmit(rating: Float, desc: String) {
        if (rating > 0)
            viewModel.fetchRateDoctor(
                "Bearer " + prefs.accessToken, rating.toString(), desc, args.id
            )
        else
            showToast("Please select rating for submitting review.")
    }

    override fun onClose() {
        files.clear()
    }
}