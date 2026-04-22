package com.example.naura_orbit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.example.naura_orbit.pertemuan_3.LoginActivity
import com.example.naura_orbit.pertemuan_4.BangunRuangActivity
import com.example.naura_orbit.pertemuan_4.Custom2Activity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 🔹 SharedPreferences
        sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // 🔹 Edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 Toolbar (WAJIB DOSEN)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 🔹 Button
        val btnBangun = findViewById<Button>(R.id.btnBangun)
        val btnBinaDesa = findViewById<Button>(R.id.btnBinaDesa)
        val btnCustom2 = findViewById<Button>(R.id.btnCustom2)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // 🔹 Bangun Ruang
        btnBangun.setOnClickListener {
            startActivity(Intent(this, BangunRuangActivity::class.java))
        }

        // 🌐 🔥 BINA DESA → WEBVIEW
        btnBinaDesa.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        // 🔹 Custom 2
        btnCustom2.setOnClickListener {
            startActivity(Intent(this, Custom2Activity::class.java))
        }

        // 🔴 LOGOUT
        btnLogout.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialog, _ ->

                    // 🔥 Hapus session login
                    sharedPref.edit().clear().apply()

                    startActivity(Intent(this, LoginActivity::class.java))
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