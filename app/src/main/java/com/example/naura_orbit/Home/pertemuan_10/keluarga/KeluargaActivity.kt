package com.example.naura_orbit.Home.pertemuan_10.keluarga

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityKeluargaBinding
import com.google.android.material.tabs.TabLayoutMediator

class KeluargaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKeluargaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeluargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Tombol Kembali
        binding.btnBackKeluarga.setOnClickListener {
            finish()
        }

        // Hubungkan Adapter ke ViewPager2
        val adapter = KeluargaTabAdapter(this)
        binding.viewPagerKeluarga.adapter = adapter

        // Berikan penamaan judul Tab yang searah dengan fungsi rumpun Warga
        TabLayoutMediator(binding.tabLayoutKeluarga, binding.viewPagerKeluarga) { tab, position ->
            when (position) {
                0 -> tab.text = "Statistik"
                1 -> tab.text = "Info Layanan"
                2 -> tab.text = "Daftar KK"
            }
        }.attach()
    }
}