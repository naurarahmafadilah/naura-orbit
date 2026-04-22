package com.example.naura_orbit.pertemuan_4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.naura_orbit.R

class BangunRuangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bangun_ruang)

        // 🔹 Handle padding (edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 Ambil komponen
        val tvJudul = findViewById<TextView>(R.id.tvJudul)
        val tvDesc = findViewById<TextView>(R.id.tvDesc)
        val inputSisi = findViewById<EditText>(R.id.inputSisi)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // 🔹 Ambil data dari Intent (pakai fallback biar tidak null)
        val judul = intent.getStringExtra("judul") ?: "Bangun Ruang"
        val desc = intent.getStringExtra("desc") ?: "Menghitung Volume Kubus"

        tvJudul.text = judul
        tvDesc.text = desc

        // ✅ Tombol HITUNG
        btnHitung.setOnClickListener {

            val sisiText = inputSisi.text.toString()

            if (sisiText.isEmpty()) {
                tvHasil.text = "Masukkan nilai sisi terlebih dahulu!"
                return@setOnClickListener
            }

            val sisi = sisiText.toDouble()
            val volume = sisi * sisi * sisi

            tvHasil.text = "Volume = $volume"
        }

        // ✅ Tombol KEMBALI
        btnBack.setOnClickListener {
            finish()
        }
    }
}