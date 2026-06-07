package com.example.naura_orbit.Note

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.naura_orbit.data.AppDatabase
import com.example.naura_orbit.data.entity.WargaEntity
import com.example.naura_orbit.databinding.ActivityNoteFormBinding
import kotlinx.coroutines.launch

class NoteFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteFormBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        /** Setup Toolbar **/
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.title = "Ajukan Permintaan"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        /** Aksi Tombol Simpan **/
        binding.btnSaveNote.setOnClickListener {
            val inputNama = binding.etTitle.text.toString().trim()
            val inputNik = binding.etContent.text.toString().trim()
            val inputPermintaan = binding.etRtrw.text.toString().trim() // Membaca otomatis dari et_rtrw

            if (inputNama.isNotBlank() && inputNik.isNotBlank() && inputPermintaan.isNotBlank()) {
                lifecycleScope.launch {
                    val requestWarga = WargaEntity(
                        nama = inputNama,
                        nik = inputNik,
                        rtRw = inputPermintaan
                    )

                    db.wargaDao().insertWarga(requestWarga)

                    Toast.makeText(this@NoteFormActivity, "Permintaan Berhasil Dikirim!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Harap isi semua kolom form!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}