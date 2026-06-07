package com.example.naura_orbit.Note

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.naura_orbit.data.AppDatabase
import com.example.naura_orbit.data.entity.WargaEntity
import com.example.naura_orbit.databinding.ActivityKkFormBinding
import kotlinx.coroutines.launch

class KkFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKkFormBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKkFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        /** Setup Toolbar **/
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.title = "Pengajuan KK"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        /** Aksi Tombol Simpan **/
        binding.btnSaveKk.setOnClickListener {
            val inputNama = binding.etNamaKk.text.toString().trim()
            val inputNik = binding.etNikKk.text.toString().trim()
            val inputDetail = binding.etDetailKk.text.toString().trim()

            if (inputNama.isNotBlank() && inputNik.isNotBlank() && inputDetail.isNotBlank()) {
                if (inputNik.length < 16) {
                    Toast.makeText(this, "NIK harus berisi 16 digit!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    // Menyusun data KK agar masuk dengan label rapi ke kolom rtRw database lama
                    val dataPengajuanKk = WargaEntity(
                        nama = "$inputNama (Pengajuan KK)",
                        nik = inputNik,
                        rtRw = "Detail Layanan: $inputDetail"
                    )

                    db.wargaDao().insertWarga(dataPengajuanKk)

                    Toast.makeText(this@KkFormActivity, "Pengajuan KK Berhasil Dikirim!", Toast.LENGTH_SHORT).show()
                    finish() // Tutup halaman dan kembali ke menu utama
                }
            } else {
                Toast.makeText(this, "Harap lengkapi semua kolom formulir KK!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}