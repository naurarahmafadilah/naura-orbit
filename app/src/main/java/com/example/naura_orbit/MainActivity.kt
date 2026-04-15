package com.example.naura_orbit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import pertemuan_3.LoginActivity
import pertemuan_4.BangunRuangActivity
import pertemuan_4.Custom1Activity
import pertemuan_4.Custom2Activity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle padding (edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 Ambil komponen dari XML
        val btnBangun = findViewById<Button>(R.id.btnBangun)
        val btnCustom1 = findViewById<Button>(R.id.btnCustom1)
        val btnCustom2 = findViewById<Button>(R.id.btnCustom2)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // 🔹 Intent ke Bangun Ruang
        btnBangun.setOnClickListener {
            val intent = Intent(this, BangunRuangActivity::class.java)
            intent.putExtra("judul", "Bangun Ruang")
            intent.putExtra("desc", "Halaman menghitung bangun ruang")
            startActivity(intent)
        }

        // 🔹 Intent ke Custom 1
        btnCustom1.setOnClickListener {
            val intent = Intent(this, Custom1Activity::class.java)
            intent.putExtra("judul", "Custom 1")
            intent.putExtra("desc", "Halaman custom pertama")
            startActivity(intent)
        }

        // 🔹 Intent ke Custom 2
        btnCustom2.setOnClickListener {
            val intent = Intent(this, Custom2Activity::class.java)
            intent.putExtra("judul", "Custom 2")
            intent.putExtra("desc", "Halaman custom kedua")
            startActivity(intent)
        }

        // 🔴 Logout (WAJIB: AlertDialog + Snackbar)
        btnLogout.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()

                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Logout dibatalkan",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                .show()
        }
    }
}