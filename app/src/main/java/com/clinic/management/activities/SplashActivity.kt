package com.clinic.management.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.clinic.management.databinding.ActivitySplashBinding
import com.clinic.management.prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding =
        ActivitySplashBinding::inflate


    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            delay(2000)
            moveToNextScreen()
        }
    }

    private fun moveToNextScreen() {
        if (TextUtils.isEmpty(prefs.accessToken)) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}