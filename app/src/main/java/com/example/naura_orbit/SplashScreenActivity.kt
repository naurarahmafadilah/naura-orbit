package com.example.naura_orbit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.pertemuan_6.BaseActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Animasi Logo Nusa Data
        val logoContainer = findViewById<android.view.View>(R.id.logo_container)
        if (logoContainer != null) {
            logoContainer.alpha = 0f
            logoContainer.scaleX = 0.8f
            logoContainer.scaleY = 0.8f
            logoContainer.animate()
                .alpha(1f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(1200)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
        }

        // Mengambil SharedPreferences
        sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            val isLogin = sharedPref.getBoolean("isLogin", false)

            if (isLogin) {
                // 🟢 Kasus 1: User sudah login -> Langsung bypass ke Dashboard Utama (BaseActivity)
                val intent = Intent(this, BaseActivity::class.java)
                startActivity(intent)
            } else {
                // 🟢 Kasus 2: User belum login -> Arahkan ke Onboarding Screen (AuthActivity) terlebih dahulu
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }

            // Menutup Splash agar tidak bisa kembali saat menekan tombol back
            finish()

        }, 2000) // Delay 2 detik
    }
}