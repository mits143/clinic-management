package com.clinic.management.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import com.clinic.management.databinding.ActivityLoginBinding
import com.clinic.management.prefs
import com.clinic.management.util.Constants.TOKEN
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.LoginViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding =
        ActivityLoginBinding::inflate

    private val viewModel: LoginViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        setObserver()
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        hud = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun login() {
        if (TextUtils.isEmpty(binding.edtMobileNumber.text.toString().trim())) {
            binding.edtMobileNumber.error = "Mobile number cannot be empty"
            binding.edtMobileNumber.requestFocus()
            return
        }
        if (TextUtils.isEmpty(binding.edtPwd.text.toString().trim())) {
            binding.edtPwd.error = "Invalid password"
            binding.edtPwd.requestFocus()
            return
        }
        viewModel.login(
            TOKEN,
            binding.edtMobileNumber.text.toString().trim(),
            binding.edtPwd.text.toString().trim(),
        )
    }

    private fun setObserver() {
        viewModel.getLoginData.observe(this) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                when (it.status) {
                    Status.LOADING -> {
                        showProgress(true)
                    }
                    Status.SUCCESS -> {
                        showProgress(false)
                        it.data?.let {
                            prefs.accessToken = it.data.token
                            prefs.userName = it.data.firstName
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                        showToast(it.message!!)
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