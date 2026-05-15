package com.example.naura_orbit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.Home.pertemuan_3.LoginActivity
import com.example.naura_orbit.pertemuan_6.BaseActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Pastikan nama file SharedPreferences ini sama dengan yang di LoginActivity ya!
        sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            val isLogin = sharedPref.getBoolean("isLogin", false)

            if (isLogin) {
                // User sudah login -> Ke Home
                val intent = Intent(this, BaseActivity::class.java)
                startActivity(intent)
            } else {
                // User belum login -> Ke Login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            // Sangat penting: Tutup Splash agar tidak bisa balik lagi pakai tombol back
            finish()

        }, 2000) // Delay 2 detik
    }
}