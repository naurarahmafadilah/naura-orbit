package com.example.naura_orbit.Home.pertemuan_10.keluarga

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
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

        // Setup Tombol Tambah
        binding.btnAddKeluarga.setOnClickListener {
            showAddKeluargaDialog()
        }

        // Hubungkan Adapter ke ViewPager2
        val adapter = KeluargaTabAdapter(this)
        binding.viewPagerKeluarga.adapter = adapter

        // Menambahkan listener pencarian untuk menyaring data kartu keluarga
        binding.etSearchKeluarga.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""
                
                // Otomatis berpindah ke tab Daftar KK saat mulai mencari agar hasilnya langsung terlihat
                if (query.trim().isNotEmpty() && binding.viewPagerKeluarga.currentItem != 2) {
                    binding.viewPagerKeluarga.currentItem = 2
                }
                
                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCKeluargaFragment) {
                    fragment.performSearch(query)
                }
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        // Berikan penamaan judul Tab yang searah dengan fungsi rumpun Warga
        TabLayoutMediator(binding.tabLayoutKeluarga, binding.viewPagerKeluarga) { tab, position ->
            when (position) {
                0 -> tab.text = "Statistik"
                1 -> tab.text = "Info Layanan"
                2 -> tab.text = "Daftar KK"
            }
        }.attach()
    }

    private fun showAddKeluargaDialog() {
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

        val etNoKk = EditText(context).apply {
            hint = "Nomor Kartu Keluarga (16 digit)"
            layoutParams = lp
            filters = arrayOf(android.text.InputFilter.LengthFilter(16))
        }
        val etKepala = EditText(context).apply {
            hint = "Nama Kepala Keluarga"
            layoutParams = lp
        }
        val etAlamat = EditText(context).apply {
            hint = "Alamat Lengkap"
            layoutParams = lp
        }
        val etJumlah = EditText(context).apply {
            hint = "Jumlah Anggota Keluarga"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            layoutParams = lp
        }

        layout.addView(etNoKk)
        layout.addView(etKepala)
        layout.addView(etAlamat)
        layout.addView(etJumlah)

        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("Tambah Kartu Keluarga (KK)")
            .setView(layout)
            .setPositiveButton("Simpan") { dialog, _ ->
                val noKk = etNoKk.text.toString().trim()
                val kepala = etKepala.text.toString().trim()
                val alamat = etAlamat.text.toString().trim()
                val jmlString = etJumlah.text.toString().trim()

                if (noKk.isEmpty() || kepala.isEmpty() || alamat.isEmpty() || jmlString.isEmpty()) {
                    Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                if (noKk.length < 16) {
                    Toast.makeText(this, "Nomor KK harus 16 digit!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val jmlAnggota = jmlString.toIntOrNull() ?: 1
                val keluargaBaru = KeluargaModel(noKk, kepala, alamat, jmlAnggota)

                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCKeluargaFragment) {
                    fragment.tambahKeluargaBaru(keluargaBaru)
                    Toast.makeText(this, "KK baru berhasil ditambahkan!", Toast.LENGTH_SHORT).show()

                    com.example.naura_orbit.Home.pertemuan_10.utils.NotificationHelper.showNotification(
                        context = this,
                        title = "NusaData — KK Baru Terdaftar 📋",
                        message = "KK dengan Kepala Keluarga $kepala berhasil terdaftar.",
                        intent = Intent(this, KeluargaActivity::class.java).apply {
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