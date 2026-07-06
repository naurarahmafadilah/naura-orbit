package com.example.naura_orbit.Home.pertemuan_10.kelahiran

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.content.Intent
import android.net.Uri
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityKelahiranBinding
import com.google.android.material.tabs.TabLayoutMediator

class KelahiranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKelahiranBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelahiranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackKelahiran.setOnClickListener {
            finish()
        }

        binding.btnAddKelahiran.setOnClickListener {
            showAddKelahiranDialog()
        }

        val adapter = KelahiranTabAdapter(this)
        binding.viewPagerKelahiran.adapter = adapter

        TabLayoutMediator(binding.tabLayoutKelahiran, binding.viewPagerKelahiran) { tab, position ->
            when (position) {
                0 -> tab.text = "Statistik"
                1 -> tab.text = "Info Layanan"
                2 -> {
                    tab.text = "Daftar Kelahiran"
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 15
                }
            }
        }.attach()

        binding.etSearchKelahiran.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                
                // Otomatis berpindah ke tab Daftar Kelahiran saat mulai mencari agar hasilnya langsung terlihat
                if (query.trim().isNotEmpty() && binding.viewPagerKelahiran.currentItem != 2) {
                    binding.viewPagerKelahiran.currentItem = 2
                }
                
                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCKelahiranFragment) {
                    fragment.performSearch(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showAddKelahiranDialog() {
        val context = this
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 40)
        }
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 16, 0, 16)
        }

        val etNamaBayi = EditText(context).apply { hint = "Nama Bayi"; layoutParams = lp }
        val etTanggal = EditText(context).apply { hint = "Tanggal Lahir (YYYY-MM-DD)"; layoutParams = lp }
        val etJk = EditText(context).apply { hint = "Jenis Kelamin (Laki-laki / Perempuan)"; layoutParams = lp }
        val etAyah = EditText(context).apply { hint = "Nama Ayah"; layoutParams = lp }
        val etIbu = EditText(context).apply { hint = "Nama Ibu"; layoutParams = lp }
        val etBerat = EditText(context).apply { hint = "Berat Lahir (misal: 3.2 Kg)"; layoutParams = lp }
        val etPanjang = EditText(context).apply { hint = "Panjang Lahir (misal: 50 Cm)"; layoutParams = lp }

        layout.addView(etNamaBayi)
        layout.addView(etTanggal)
        layout.addView(etJk)
        layout.addView(etAyah)
        layout.addView(etIbu)
        layout.addView(etBerat)
        layout.addView(etPanjang)

        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("Tambah Catatan Kelahiran")
            .setView(layout)
            .setPositiveButton("Simpan") { dialog, _ ->
                val namaBayi = etNamaBayi.text.toString().trim()
                val tanggal = etTanggal.text.toString().trim()
                val jk = etJk.text.toString().trim()
                val ayah = etAyah.text.toString().trim()
                val ibu = etIbu.text.toString().trim()
                val berat = etBerat.text.toString().trim()
                val panjang = etPanjang.text.toString().trim()

                if (namaBayi.isEmpty() || tanggal.isEmpty() || jk.isEmpty() || ayah.isEmpty() || ibu.isEmpty() || berat.isEmpty() || panjang.isEmpty()) {
                    Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val kelahiranBaru = KelahiranModel(namaBayi, tanggal, jk, ayah, ibu, berat, panjang)

                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCKelahiranFragment) {
                    fragment.tambahKelahiranBaru(kelahiranBaru)
                    Toast.makeText(this, "Catatan kelahiran berhasil ditambahkan!", Toast.LENGTH_SHORT).show()

                    com.example.naura_orbit.Home.pertemuan_10.utils.NotificationHelper.showNotification(
                        context = this,
                        title = "NusaData — Kelahiran Terdata 👶",
                        message = "Bayi bernama $namaBayi berhasil didaftarkan di sistem.",
                        intent = Intent(this, KelahiranActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        }
                    )
                } else {
                    Toast.makeText(this, "Gagal menambahkan data ke tab list", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
