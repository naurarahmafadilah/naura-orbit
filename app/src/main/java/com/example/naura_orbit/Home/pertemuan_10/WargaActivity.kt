package com.example.naura_orbit.Home.pertemuan_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityWargaBinding
import com.google.android.material.tabs.TabLayoutMediator

class WargaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWargaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Menginflate layout activity_warga.xml premium
        binding = ActivityWargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. Aksi Klik Tombol Back Manual (Menggunakan ImageView premium btnBackWarga)
        binding.btnBackWarga.setOnClickListener {
            finish()
        }

        // 3. Inisialisasi Adapter Utama (Pastikan kelas WargaTabsAdapter sudah kamu buat)
        val tabsAdapter = WargaTabsAdapter(this)
        binding.viewPagerWarga.adapter = tabsAdapter

        // 4. Hubungkan TabLayoutWarga & ViewPagerWarga dengan Judul & Badge
        TabLayoutMediator(binding.tabLayoutWarga, binding.viewPagerWarga) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Ringkasan"
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    // Opsional: badge.backgroundColor = Color.RED
                }
                1 -> {
                    tab.text = "Kontak RT/RW"
                }
                2 -> {
                    tab.text = "Daftar Warga"
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 3 // Sinkron dengan visual 3 data warga
                }
            }
        }.attach()
    }
}