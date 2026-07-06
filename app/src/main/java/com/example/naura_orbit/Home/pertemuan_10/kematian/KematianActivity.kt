package com.example.naura_orbit.Home.pertemuan_10.kematian

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.content.Intent
import android.net.Uri
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityKematianBinding
import com.google.android.material.tabs.TabLayoutMediator

class KematianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKematianBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKematianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackKematian.setOnClickListener {
            finish()
        }

        binding.btnAddKematian.setOnClickListener {
            showAddKematianDialog()
        }

        val adapter = KematianTabAdapter(this)
        binding.viewPagerKematian.adapter = adapter

        TabLayoutMediator(binding.tabLayoutKematian, binding.viewPagerKematian) { tab, position ->
            when (position) {
                0 -> tab.text = "Statistik"
                1 -> tab.text = "Info Layanan"
                2 -> {
                    tab.text = "Daftar Kematian"
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 12
                }
            }
        }.attach()

        binding.etSearchKematian.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                
                // Otomatis berpindah ke tab Daftar Kematian saat mulai mencari agar hasilnya langsung terlihat
                if (query.trim().isNotEmpty() && binding.viewPagerKematian.currentItem != 2) {
                    binding.viewPagerKematian.currentItem = 2
                }
                
                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCKematianFragment) {
                    fragment.performSearch(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showAddKematianDialog() {
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

        val etNama = EditText(context).apply { hint = "Nama Jenazah"; layoutParams = lp }
        val etNik = EditText(context).apply { hint = "Nomor NIK (16 digit)"; layoutParams = lp; filters = arrayOf(android.text.InputFilter.LengthFilter(16)) }
        val etTanggal = EditText(context).apply { hint = "Tanggal Wafat (YYYY-MM-DD)"; layoutParams = lp }
        val etJk = EditText(context).apply { hint = "Jenis Kelamin (Laki-laki / Perempuan)"; layoutParams = lp }
        val etPenyebab = EditText(context).apply { hint = "Penyebab Wafat"; layoutParams = lp }
        val etTempat = EditText(context).apply { hint = "Tempat Wafat"; layoutParams = lp }
        val etMakam = EditText(context).apply { hint = "Tempat Pemakaman"; layoutParams = lp }

        layout.addView(etNama)
        layout.addView(etNik)
        layout.addView(etTanggal)
        layout.addView(etJk)
        layout.addView(etPenyebab)
        layout.addView(etTempat)
        layout.addView(etMakam)

        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("Tambah Catatan Kematian")
            .setView(layout)
            .setPositiveButton("Simpan") { dialog, _ ->
                val nama = etNama.text.toString().trim()
                val nik = etNik.text.toString().trim()
                val tanggal = etTanggal.text.toString().trim()
                val jk = etJk.text.toString().trim()
                val penyebab = etPenyebab.text.toString().trim()
                val tempat = etTempat.text.toString().trim()
                val makam = etMakam.text.toString().trim()

                if (nama.isEmpty() || nik.isEmpty() || tanggal.isEmpty() || jk.isEmpty() || penyebab.isEmpty() || tempat.isEmpty() || makam.isEmpty()) {
                    Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                if (nik.length < 16) {
                    Toast.makeText(this, "NIK harus 16 digit!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val kematianBaru = KematianModel(nama, nik, tanggal, jk, penyebab, tempat, makam)

                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCKematianFragment) {
                    fragment.tambahKematianBaru(kematianBaru)
                    Toast.makeText(this, "Catatan kematian berhasil ditambahkan!", Toast.LENGTH_SHORT).show()

                    com.example.naura_orbit.Home.pertemuan_10.utils.NotificationHelper.showNotification(
                        context = this,
                        title = "NusaData — Kematian Terdata 📋",
                        message = "Pencatatan wafat atas nama $nama berhasil disimpan.",
                        intent = Intent(this, KematianActivity::class.java).apply {
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
