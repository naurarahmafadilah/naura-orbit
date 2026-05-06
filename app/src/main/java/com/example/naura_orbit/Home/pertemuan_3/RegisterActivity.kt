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
            val nama = binding.etNama.text.toString()
            val phone = binding.etPhone.text.toString()
            val email = binding.etEmail.text.toString()
            val user = binding.etUsername.text.toString()
            val pass = binding.etPassword.text.toString()

            if (nama.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && user.isNotEmpty() && pass.isNotEmpty()) {
                val intent = Intent(this, VerificationActivity::class.java).apply {
                    putExtra("EXTRA_PHONE", phone)
                    putExtra("EXTRA_USER", user)
                    putExtra("EXTRA_PASS", pass)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Harap isi semua 5 inputan!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}