package com.example.naura_orbit.Home.pertemuan_3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityVerificationBinding

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

            if (otpInput == phoneFromReg && otpInput.isNotEmpty()) {

                // 1. Simpan Data ke SharedPreferences
                val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("reg_user", userFromReg)
                editor.putString("reg_pass", passFromReg)
                editor.putString("username", userFromReg) // Agar Dashboard bisa panggil nama user
                editor.putBoolean("isLogin", true)
                editor.apply() // Pastikan data tertulis sebelum pindah halaman

                // 2. Navigasi ke MainActivity
                try {
                    // Gunakan Alamat Lengkap agar tidak "tersesat" antar folder
                    val intent = Intent(this, com.example.naura_orbit.MainActivity::class.java)

                    // Flags ini wajib agar saat di Dashboard, user tidak bisa Back ke OTP
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    // Jika crash, ini akan memberitahu nama errornya di layar
                    Toast.makeText(this, "Error Navigasi: ${e.message}", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this, "OTP Salah! Coba masukkan: $phoneFromReg", Toast.LENGTH_LONG).show()
            }
        }
    }
}