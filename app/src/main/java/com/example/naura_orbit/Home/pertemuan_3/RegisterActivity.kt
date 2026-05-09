package com.example.naura_orbit.Home.pertemuan_3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterSubmit.setOnClickListener {
            // Gunakan .trim() untuk menghindari spasi yang tidak sengaja terinput
            val nama = binding.etNama.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val user = binding.etUsername.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()

            if (nama.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() &&
                user.isNotEmpty() && pass.isNotEmpty()) {

                // Pindah ke VerificationActivity dengan membawa data registrasi
                val intent = Intent(this, VerificationActivity::class.java).apply {
                    putExtra("EXTRA_PHONE", phone)
                    putExtra("EXTRA_USER", user)
                    putExtra("EXTRA_PASS", pass)
                    // Opsional: kirim nama juga jika ingin ditampilkan di Dashboard nanti
                    putExtra("EXTRA_NAMA", nama)
                }
                startActivity(intent)

            } else {
                Toast.makeText(this, "Harap isi semua inputan!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}