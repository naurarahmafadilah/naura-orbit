package com.example.naura_orbit.Home.pertemuan_3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// Hapus import MainActivity jika sudah tidak digunakan
// import com.example.naura_orbit.MainActivity
import com.example.naura_orbit.databinding.ActivityLoginBinding
import com.example.naura_orbit.pertemuan_6.BaseActivity

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

            // 1. Validasi Input Kosong
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Isi semua field!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Logika Utama: Username harus sama dengan Password
            // ATAU sesuai dengan data yang didaftarkan di SharedPreferences
            val regUser = sharedPref.getString("reg_user", "") ?: ""
            val regPass = sharedPref.getString("reg_pass", "") ?: ""

            val isSameInput = (user == pass) // Ini logika yang kamu minta
            val isMatchAccount = (user == regUser && pass == regPass && regUser.isNotEmpty())

            if (isSameInput || isMatchAccount) {
                // Simpan status login
                sharedPref.edit().apply {
                    putBoolean("isLogin", true)
                    putString("current_user", user)
                    apply()
                }

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                navigateToDashboard()
            } else {
                Toast.makeText(this, "Username dan Password harus sama!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // 2. FUNGSI NAVIGASI KE DASHBOARD/HOME
    private fun navigateToDashboard() {
        try {
            // Ubah DashboardActivity::class.java dengan nama Activity
            // yang menampung HomeFragment kamu
            val intent = Intent(this, BaseActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal pindah halaman: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}