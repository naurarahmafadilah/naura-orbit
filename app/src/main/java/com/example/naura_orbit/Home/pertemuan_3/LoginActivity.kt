package com.example.naura_orbit.Home.pertemuan_3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityLoginBinding
import com.example.naura_orbit.pertemuan_6.BaseActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Inisialisasi View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. Inisialisasi SharedPreferences (Gunakan nama yang sama dengan SplashScreen)
        sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Cek jika sudah login sebelumnya (Auto Login)
        if (sharedPref.getBoolean("isLogin", false)) {
            navigateToBase()
        }

        binding.btnLogin.setOnClickListener {
            val user = binding.etUsername.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()

            // 3. Validasi Input
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Harap isi semua kolom!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 4. Ambil data dari Register (Jika ada)
            val regUser = sharedPref.getString("reg_user", "") ?: ""
            val regPass = sharedPref.getString("reg_pass", "") ?: ""

            // 5. Logika Login: Username == Password ATAU Sesuai Register
            val isSameInput = (user == pass)
            val isMatchAccount = (user == regUser && pass == regPass && regUser.isNotEmpty())

            if (isSameInput || isMatchAccount) {
                saveLoginSession(user)
            } else {
                Toast.makeText(this, "Login Gagal! Akun tidak sesuai.", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol ke Register
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun saveLoginSession(username: String) {
        // Simpan status isLogin = true agar Splash Screen langsung ke Home nantinya
        sharedPref.edit().apply {
            putBoolean("isLogin", true)
            putString("current_user", username)
            apply()
        }

        Toast.makeText(this, "Login Berhasil, Halo $username!", Toast.LENGTH_SHORT).show()
        navigateToBase()
    }

    private fun navigateToBase() {
        try {
            // Kita pindah ke BaseActivity (bukan HomeFragment langsung)
            // Karena BaseActivity yang memegang Bottom Navigation dan Container Fragment
            val intent = Intent(this, BaseActivity::class.java)

            // Hapus tumpukan Activity agar tidak bisa klik 'Back' ke halaman Login
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Terjadi kesalahan navigasi", Toast.LENGTH_SHORT).show()
        }
    }
}