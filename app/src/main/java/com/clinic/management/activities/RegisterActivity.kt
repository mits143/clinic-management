package com.clinic.management.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.RadioButton
import com.clinic.management.databinding.ActivityRegisterBinding
import com.clinic.management.prefs
import com.clinic.management.util.Constants
import com.clinic.management.util.Status
import com.clinic.management.viewmodel.RegisterViewModel
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityRegisterBinding =
        ActivityRegisterBinding::inflate

    private val viewModel: RegisterViewModel by viewModel()

    private lateinit var hud: KProgressHUD

    private var gender = ""

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        setObserver()
        binding.rbGender.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            gender = radioButton.text.toString().trim()

        }
        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.btnRegister.setOnClickListener {
            register()
        }
        hud = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun register() {
        if (TextUtils.isEmpty(binding.edtFullName.text.toString().trim())) {
            binding.edtFullName.error = "Full name cannot be empty"
            binding.edtFullName.requestFocus()
            return
        }
        if (TextUtils.isEmpty(binding.edtMobileNumber.text.toString().trim())) {
            binding.edtMobileNumber.error = "Mobile number cannot be empty"
            binding.edtMobileNumber.requestFocus()
            return
        }
        if (TextUtils.isEmpty(binding.edtEmail.text.toString().trim())) {
            binding.edtEmail.error = "Email cannot be empty"
            binding.edtEmail.requestFocus()
            return
        }
        if (TextUtils.isEmpty(binding.edtAddress.text.toString().trim())) {
            binding.edtAddress.error = "Address cannot be empty"
            binding.edtAddress.requestFocus()
            return
        }
        if (TextUtils.isEmpty(binding.edtPwd.text.toString().trim())) {
            binding.edtPwd.error = "password cannot be empty"
            binding.edtPwd.requestFocus()
            return
        }
        if (!TextUtils.equals(
                binding.edtPwd.text.toString().trim(),
                binding.edtConfirmPwd.text.toString().trim()
            )
        ) {
            binding.edtConfirmPwd.error = "Confirm password mismatch"
            binding.edtConfirmPwd.requestFocus()
            return
        }
        viewModel.register(
            Constants.TOKEN,
            binding.edtEmail.text.toString().trim(),
            binding.edtPwd.text.toString().trim(),
            binding.edtFullName.text.toString().trim(),
            binding.edtMobileNumber.text.toString().trim(),
            binding.edtAddress.text.toString().trim(),
            gender,
            ""
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
//                        showToast(it.message!!)
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
                        if (it.message == "Invalid authentication.") {
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
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
