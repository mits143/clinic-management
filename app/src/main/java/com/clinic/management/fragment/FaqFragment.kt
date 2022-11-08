package com.clinic.management.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clinic.management.activities.LoginActivity
import com.clinic.management.databinding.FragmentFaqBinding
import com.clinic.management.prefs
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.FAQViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaqFragment : BaseFragment<FragmentFaqBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFaqBinding =
        FragmentFaqBinding::inflate

    private val viewModel: FAQViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.imgMenu.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun setObserver() {
        viewModel.fetchFAQData("Bearer " + prefs.accessToken)
        viewModel.getFAQData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {

                            binding.txtFaq.text =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    Html.fromHtml(
                                        it["data"].asJsonArray[0].asJsonObject["editor"].asString,
                                        Html.FROM_HTML_MODE_COMPACT
                                    )
                                } else {
                                    Html.fromHtml(it["data"].asJsonArray[0].asJsonObject["editor"].asString)
                                }
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