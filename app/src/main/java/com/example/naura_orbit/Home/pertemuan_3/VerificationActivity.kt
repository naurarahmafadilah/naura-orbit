package com.example.naura_orbit.Home.pertemuan_3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// Hapus import MainActivity karena sudah tidak dipakai
// import com.example.naura_orbit.MainActivity
import com.example.naura_orbit.databinding.ActivityVerificationBinding
import com.example.naura_orbit.pertemuan_6.BaseActivity

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val phoneFromReg = intent.getStringExtra("EXTRA_PHONE") ?: ""
        val userFromReg = intent.getStringExtra("EXTRA_USER") ?: ""
        val passFromReg = intent.getStringExtra("EXTRA_PASS") ?: ""

        binding.btnVerify.setOnClickListener {
            val otpInput = binding.etOtp.text.toString().trim()

            // Simulasi OTP (menggunakan nomor HP sebagai kode OTP)
            if (otpInput == phoneFromReg && otpInput.isNotEmpty()) {

                // 1. Simpan Data ke SharedPreferences
                val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("reg_user", userFromReg)
                editor.putString("reg_pass", passFromReg)
                editor.putString("username", userFromReg)
                editor.putBoolean("isLogin", true) // Set auto-login jadi true
                editor.apply()

                // 2. Navigasi ke Dashboard/Home (Bukan MainActivity)
                navigateToDashboard()

            } else {
                Toast.makeText(this, "OTP Salah! Masukkan: $phoneFromReg", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToDashboard() {
        try {
            // GANTI DashboardActivity dengan Activity yang menampung HomeFragment kamu
            val intent = Intent(this, BaseActivity::class.java)

            // Flags untuk menghapus history agar tidak bisa back ke halaman Verifikasi
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Error Navigasi: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}