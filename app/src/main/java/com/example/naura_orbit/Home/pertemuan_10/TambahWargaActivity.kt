package com.example.naura_orbit.Home.pertemuan_10

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityTambahWargaBinding
import com.example.naura_orbit.Home.pertemuan_10.utils.NotificationHelper
import com.example.naura_orbit.Home.pertemuan_10.utils.PermissionHelper

class TambahWargaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahWargaBinding

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Izin ditolak, pengingat tidak dapat diaktifkan", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahWargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ==========================================
        //  SOLUSI: PASANG AKSI KLIK TOMBOL BACK DI SINI
        // ==========================================
        binding.btnBackTambah.setOnClickListener {
            finish() // Berfungsi untuk menutup activity form dan kembali ke halaman sebelumnya
        }

        // Cek Izin Notifikasi (Android 13+)
        if (PermissionHelper.isNotificationPermissionRequired()) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (!PermissionHelper.hasPermission(this, permission)) {
                PermissionHelper.requestPermission(notificationPermissionLauncher, permission)
            }
        }

        // Logika ketika Tombol Simpan/Submit diklik
        binding.btnSimpanWarga.setOnClickListener {
            val nama = binding.inputNamaWarga.text.toString().trim()
            val nik = binding.inputNik.text.toString().trim()
            val wilayah = binding.inputWilayah.text.toString().trim()
            val mntReminder = binding.inputMenitReminder.text.toString().toLongOrNull() ?: 0L

            if (nama.isEmpty() || nik.isEmpty() || wilayah.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data warga!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nik.length < 16) {
                Toast.makeText(this, "NIK harus berjumlah 16 digit!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Lempar data kembali ke WargaActivity
            val intentKembali = Intent().apply {
                putExtra("EXTRA_NAMA", nama)
                putExtra("EXTRA_NIK", nik)
                putExtra("EXTRA_WILAYAH", wilayah)
            }
            setResult(Activity.RESULT_OK, intentKembali)

            // Picu Notifikasi Sukses
            val intentKeHalamanWarga = Intent(this, WargaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }

            NotificationHelper.showNotification(
                context = this,
                title = "NusaData — Pendaftaran Sukses Warga",
                message = "Warga baru bernama $nama (NIK: $nik) berhasil terdata di sistem.",
                intent = intentKeHalamanWarga
            )

            // Set Up WorkManager Reminder
            if (mntReminder > 0) {
                NotificationHelper.scheduleReminder(
                    context = this,
                    minutes = mntReminder,
                    title = "NusaData — Jadwal Validasi Berkas 📋",
                    message = "Waktunya melakukan verifikasi data fisik lapangan ke wilayah $wilayah untuk warga bernama $nama."
                )
                Toast.makeText(this, "Data disimpan & pengingat disetel $mntReminder menit lagi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Data warga berhasil ditambahkan!!", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }
}