package com.example.naura_orbit.Home.pertemuan_10.pindah

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.content.Intent
import android.net.Uri
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityPindahBinding
import com.google.android.material.tabs.TabLayoutMediator

class PindahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPindahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPindahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackPindah.setOnClickListener {
            finish()
        }

        binding.btnAddPindah.setOnClickListener {
            showAddPindahDialog()
        }

        val adapter = PindahTabAdapter(this)
        binding.viewPagerPindah.adapter = adapter

        TabLayoutMediator(binding.tabLayoutPindah, binding.viewPagerPindah) { tab, position ->
            when (position) {
                0 -> tab.text = "Statistik"
                1 -> tab.text = "Info Layanan"
                2 -> {
                    tab.text = "Daftar Pindah"
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 18
                }
            }
        }.attach()

        binding.etSearchPindah.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                
                // Otomatis berpindah ke tab Daftar Pindah saat mulai mencari agar hasilnya langsung terlihat
                if (query.trim().isNotEmpty() && binding.viewPagerPindah.currentItem != 2) {
                    binding.viewPagerPindah.currentItem = 2
                }
                
                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCPindahFragment) {
                    fragment.performSearch(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showAddPindahDialog() {
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

        val etNama = EditText(context).apply { hint = "Nama Warga"; layoutParams = lp }
        val etNik = EditText(context).apply { hint = "Nomor NIK (16 digit)"; layoutParams = lp; filters = arrayOf(android.text.InputFilter.LengthFilter(16)) }
        val etJenis = EditText(context).apply { hint = "Jenis Mutasi (Masuk / Keluar)"; layoutParams = lp }
        val etTanggal = EditText(context).apply { hint = "Tanggal Mutasi (YYYY-MM-DD)"; layoutParams = lp }
        val etAsal = EditText(context).apply { hint = "Alamat Asal"; layoutParams = lp }
        val etTujuan = EditText(context).apply { hint = "Alamat Tujuan"; layoutParams = lp }
        val etAlasan = EditText(context).apply { hint = "Alasan Mutasi"; layoutParams = lp }

        layout.addView(etNama)
        layout.addView(etNik)
        layout.addView(etJenis)
        layout.addView(etTanggal)
        layout.addView(etAsal)
        layout.addView(etTujuan)
        layout.addView(etAlasan)

        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("Tambah Catatan Pindah")
            .setView(layout)
            .setPositiveButton("Simpan") { dialog, _ ->
                val nama = etNama.text.toString().trim()
                val nik = etNik.text.toString().trim()
                val jenis = etJenis.text.toString().trim()
                val tanggal = etTanggal.text.toString().trim()
                val asal = etAsal.text.toString().trim()
                val tujuan = etTujuan.text.toString().trim()
                val alasan = etAlasan.text.toString().trim()

                if (nama.isEmpty() || nik.isEmpty() || jenis.isEmpty() || tanggal.isEmpty() || asal.isEmpty() || tujuan.isEmpty() || alasan.isEmpty()) {
                    Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                if (nik.length < 16) {
                    Toast.makeText(this, "NIK harus 16 digit!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val pindahBaru = PindahModel(nama, nik, jenis, tanggal, asal, tujuan, alasan)

                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCPindahFragment) {
                    fragment.tambahPindahBaru(pindahBaru)
                    Toast.makeText(this, "Catatan perpindahan berhasil ditambahkan!", Toast.LENGTH_SHORT).show()

                    com.example.naura_orbit.Home.pertemuan_10.utils.NotificationHelper.showNotification(
                        context = this,
                        title = "NusaData — Mutasi Terdata 📋",
                        message = "Pencatatan mutasi kependudukan untuk $nama berhasil disimpan.",
                        intent = Intent(this, PindahActivity::class.java).apply {
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
