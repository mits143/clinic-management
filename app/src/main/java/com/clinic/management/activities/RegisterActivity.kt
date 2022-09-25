package com.clinic.management.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.clinic.management.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityRegisterBinding =
        ActivityRegisterBinding::inflate

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
