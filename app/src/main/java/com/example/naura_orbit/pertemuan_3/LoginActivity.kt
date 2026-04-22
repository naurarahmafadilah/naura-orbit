package com.example.naura_orbit.pertemuan_3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.naura_orbit.MainActivity
import com.example.naura_orbit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 🔹 Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 SharedPreferences
        sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // 🔥 FIX ERROR DI SINI (PAKAI root, BUKAN main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 LOGIN BUTTON
        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // ✅ SESUAI MODUL (username = password)
            if (username == password && username.isNotEmpty()) {

                // 🔹 Simpan login
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                // 🔹 Pindah ke Main
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } else {
                binding.etUsername.error = "Username salah"
                binding.etPassword.error = "Password salah"
            }
        }
    }
}