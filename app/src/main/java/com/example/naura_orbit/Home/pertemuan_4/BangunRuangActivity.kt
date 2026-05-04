package com.example.naura_orbit.Home.pertemuan_4

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.R
import com.example.naura_orbit.databinding.ActivityBangunRuangBinding // Nama binding mengikuti nama file XML
import java.text.DecimalFormat

class BangunRuangActivity : AppCompatActivity() {

    // Inisialisasi binding dengan nama yang sesuai
    private lateinit var binding: ActivityBangunRuangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate layout menggunakan binding
        binding = ActivityBangunRuangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupLogic()
    }

    private fun setupToolbar() {
        // ID toolbar sesuai dengan XML pro yang kita buat tadi (toolbarKubus atau toolbarBangunRuang)
        setSupportActionBar(binding.toolbarKubus)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbarKubus.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupLogic() {
        // Ambil data dari Intent
        val judul = intent.getStringExtra("judul") ?: "Volume Kubus"
        // binding.tvJudul.text = judul // Sesuaikan ID jika ingin mengubah judul secara dinamis

        binding.btnHitung.setOnClickListener {
            val sisiText = binding.inputSisi.text.toString()

            if (sisiText.isEmpty()) {
                binding.inputSisi.error = "Nilai sisi tidak boleh kosong"
                return@setOnClickListener
            }

            try {
                val sisi = sisiText.toDouble()

                // Menghitung Volume: s pangkat 3
                val volume = Math.pow(sisi, 3.0)

                // Mempercantik tampilan angka (maksimal 2 desimal)
                val df = DecimalFormat("#.##")
                val hasilFormatted = df.format(volume)

                // Set hasil ke TextView
                binding.tvHasil.text = hasilFormatted

                // Efek animasi halus saat hasil muncul
                val anim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
                binding.cardHasil.startAnimation(anim)

            } catch (e: Exception) {
                Toast.makeText(this, "Masukkan angka yang valid!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}