package com.clinic.management.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.clinic.management.R
import com.clinic.management.adapter.ImagesAdapter
import com.clinic.management.databinding.DialogUploadFileBinding

class MyCustomDialog(onbuttonClick: onButtonClick) : DialogFragment() {

    lateinit var binding: DialogUploadFileBinding
    var onClick = onbuttonClick

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.gradient1);
        binding = DialogUploadFileBinding.inflate(inflater, container, false);
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
        binding.imgClose.setOnClickListener {
            onClick.onClose()
            dialog?.cancel()
        }
        binding.imgPhoto.setOnClickListener {
            onClick.onClickSelectFile()
        }
        binding.btnUploadFile.setOnClickListener {
            onClick.onClickUploadFile(
                binding.edtTitle.text.toString().trim(),
                binding.edtDesc.text.toString().trim()
            )
            dialog?.cancel()
        }
    }

    fun bindImages(adapter: ImagesAdapter) {
        binding.rvImages.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    interface onButtonClick {
        fun onClickSelectFile()
        fun onClickUploadFile(title: String, desc: String)
        fun onClose()
    }

}