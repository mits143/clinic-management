package com.clinic.management.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.clinic.management.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }
    }

//    private fun moveToNextScreen() {
//        if (TextUtils.isEmpty(prefs.accessToken)) {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        } else {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }
}