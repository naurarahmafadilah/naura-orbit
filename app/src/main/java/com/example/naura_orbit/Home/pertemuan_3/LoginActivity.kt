package com.example.naura_orbit.Home.pertemuan_3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// Import ini harus tepat mengarah ke package utama
import com.example.naura_orbit.MainActivity
import com.example.naura_orbit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // 1. AUTO LOGIN CHECK
        if (sharedPref.getBoolean("isLogin", false)) {
            navigateToDashboard()
        }

        binding.btnLogin.setOnClickListener {
            val user = binding.etUsername.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()

            // Ambil data dengan nilai default string kosong "" bukan null
            val regUser = sharedPref.getString("reg_user", "") ?: ""
            val regPass = sharedPref.getString("reg_pass", "") ?: ""

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Isi semua field!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if ((user == pass) || (user == regUser && pass == regPass)) {
                sharedPref.edit().putBoolean("isLogin", true).apply()

                val intent = Intent(this, com.example.naura_orbit.MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username/Password Salah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // 3. FUNGSI NAVIGASI (SOLUSI ANTI-GAGAL)
    private fun navigateToDashboard() {
        try {
            // Gunakan class reference secara eksplisit
            val intent = Intent(this, MainActivity::class.java)
            // Flag untuk membersihkan riwayat halaman agar tidak bisa 'back' ke Login
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Navigasi Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}