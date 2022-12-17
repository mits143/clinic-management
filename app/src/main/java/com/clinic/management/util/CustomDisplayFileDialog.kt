package com.clinic.management.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.clinic.management.databinding.DialogViewFileBinding

class CustomDisplayFileDialog(file: String) : DialogFragment() {

    lateinit var binding: DialogViewFileBinding
    var file = file

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);
        binding = DialogViewFileBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog?.window?.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog!!.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(file).into(binding.image)
        binding.imgClose.setOnClickListener {
            dialog?.cancel()
        }
    }

}