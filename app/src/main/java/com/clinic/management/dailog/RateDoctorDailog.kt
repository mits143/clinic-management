package com.clinic.management.dailog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.clinic.management.R
import com.clinic.management.databinding.DialogRateDoctorBinding

class RateDoctorDailog(onbuttonClick: onButtonClick) : DialogFragment() {

    lateinit var binding: DialogRateDoctorBinding
    var onClick = onbuttonClick

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.gradient1);
        binding = DialogRateDoctorBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog?.window?.setLayout(
            width, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog!!.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgClose.setOnClickListener {
            onClick.onClose()
            dialog?.cancel()
        }

        binding.btnSubmit.setOnClickListener {
            onClick.onClickSubmit(
                binding.ratingBar.rating.toString(),
                binding.edtDesc.text.toString().trim()
            )
            binding.edtDesc.setText("")
            dialog?.cancel()
        }
    }

    interface onButtonClick {
        fun onClickSubmit(rating: String, desc: String)
        fun onClose()
    }

}