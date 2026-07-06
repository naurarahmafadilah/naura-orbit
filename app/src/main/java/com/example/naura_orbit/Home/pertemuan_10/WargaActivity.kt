package com.example.naura_orbit.Home.pertemuan_10

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityWargaBinding
import com.google.android.material.tabs.TabLayoutMediator

class WargaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWargaBinding

    // 1. Launcher untuk menangkap data dari form pendaftaran warga setelah disubmit
    private val formResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val nama = data?.getStringExtra("EXTRA_NAMA") ?: ""
            val nik = data?.getStringExtra("EXTRA_NIK") ?: ""
            val wilayah = data?.getStringExtra("EXTRA_WILAYAH") ?: ""

            // "f2" adalah tag internal bawaan ViewPager2 untuk Fragment posisi ke-3 (TabCFragment)
            val daftarWargaFragment = supportFragmentManager.findFragmentByTag("f2")
            if (daftarWargaFragment is TabCFragment) {

                // PERBAIKAN: Parameter diselaraskan 100% dengan struktur data class WargaModel asli kamu
                val wargaBaru = WargaModel(
                    nama = nama,
                    noKtp = nik,                         // Diubah dari nik -> noKtp
                    jenisKelamin = "Laki-laki",          // Nilai default awal
                    agama = "Islam",
                    pekerjaan = "Warga Wilayah $wilayah",
                    telp = "—",                          // Diubah dari noTelp -> telp
                    email = "—"
                )

                // Panggil fungsi tambah data di TabCFragment
                daftarWargaFragment.tambahDataWargaBaru(wargaBaru)

                // Update angka badge di Tab Daftar Warga secara dinamis (+1 angka dari total data awal)
                binding.tabLayoutWarga.getTabAt(2)?.badge?.let { badge ->
                    badge.number = badge.number + 1
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aksi Klik Tombol Back Manual
        binding.btnBackWarga.setOnClickListener {
            finish()
        }

        // Aksi Klik Tombol Tambah Warga (+) di Pojok Kanan Atas Header Premium (Membuka Form Tambah Warga Lokal)
        binding.btnAddWarga.setOnClickListener {
            val intent = Intent(this, TambahWargaActivity::class.java)
            formResultLauncher.launch(intent)
        }

        // Menambahkan listener pencarian untuk menyaring data warga secara dinamis
        binding.etSearchWarga.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""
                
                // Otomatis berpindah ke tab Daftar Warga saat mulai mencari agar hasilnya langsung terlihat
                if (query.trim().isNotEmpty() && binding.viewPagerWarga.currentItem != 2) {
                    binding.viewPagerWarga.currentItem = 2
                }
                
                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCFragment) {
                    fragment.performSearch(query)
                }
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        // Inisialisasi Adapter Utama
        val tabsAdapter = WargaTabsAdapter(this)
        binding.viewPagerWarga.adapter = tabsAdapter

        // Hubungkan TabLayoutWarga & ViewPagerWarga dengan Judul & Badge
        TabLayoutMediator(binding.tabLayoutWarga, binding.viewPagerWarga) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Ringkasan"
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                }
                1 -> {
                    tab.text = "Kontak RT/RW"
                }
                2 -> {
                    tab.text = "Daftar Warga"
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 50 // Sinkron dengan jumlah data tiruan awal di TabCFragment
                }
            }
        }.attach()
    }
}