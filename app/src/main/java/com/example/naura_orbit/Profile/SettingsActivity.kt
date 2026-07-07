package com.example.naura_orbit.Profile

import android.os.Bundle
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // 1. Inisialisasi Semua Komponen Menu Pengaturan
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val switchPrivacy = findViewById<SwitchMaterial>(R.id.switchPrivacy)
        val switchNotifications = findViewById<SwitchMaterial>(R.id.switchNotifications)
        val btnChangePassword = findViewById<RelativeLayout>(R.id.btnChangePassword)
        val btnLanguage = findViewById<RelativeLayout>(R.id.btnLanguage)
        val btnLogout = findViewById<MaterialButton>(R.id.btnLogout)

        // Ambil TextView di dalam btnLanguage untuk mengubah teks sub-judul bahasa secara dinamis
        val txtCurrentLanguage = findViewById<TextView>(R.id.txtCurrentLanguage)

        // 2. Akses Tombol Kembali
        btnBack.setOnClickListener {
            finish()
        }

        // 3. Akses Switch Privasi
        switchPrivacy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Privasi Akun Diaktifkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Privasi Akun Dinonaktifkan", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Akses Switch Notifikasi
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Notifikasi PUSH Diaktifkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifikasi PUSH Dinonaktifkan", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. Akses Menu Ubah Password
        btnChangePassword.setOnClickListener {
            Toast.makeText(this, "Membuka halaman Ubah Password...", Toast.LENGTH_SHORT).show()
        }

        // 6. Akses Menu Ganti Bahasa dengan Pop-up Pilihan (AlertDialog)
        btnLanguage.setOnClickListener {
            // Daftar bahasa yang bisa dipilih
            val listBahasa = arrayOf("Bahasa Indonesia", "English (US)", "Español", "日本語")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pilih Bahasa / Select Language")

            // Set item yang bisa dipilih beserta fungsinya
            builder.setItems(listBahasa) { dialog, posisi ->
                val bahasaTerpilih = listBahasa[posisi]

                // FIXED: Memperbaiki nama variabel typo dari $bahasaTerpilled menjadi $bahasaTerpilih
                Toast.makeText(this, "Bahasa diubah ke: $bahasaTerpilih", Toast.LENGTH_SHORT).show()

                // Mengubah teks sub-judul bahasa di layar secara langsung
                txtCurrentLanguage?.text = bahasaTerpilih

                dialog.dismiss() // Tutup pop-up setelah memilih
            }

            // Tampilkan pop-up ke layar
            val dialog = builder.create()
            dialog.show()
        }

        // 7. Akses Tombol Keluar (Log Out)
        btnLogout.setOnClickListener {
            Toast.makeText(this, "Berhasil keluar dari akun", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}