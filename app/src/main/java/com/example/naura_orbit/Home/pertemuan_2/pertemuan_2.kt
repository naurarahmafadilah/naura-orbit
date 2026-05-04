package com.example.naura_orbit.Home.pertemuan_2

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.naura_orbit.R
import com.google.android.material.appbar.MaterialToolbar

class pertemuan_2 : AppCompatActivity() {

    lateinit var alas: EditText
    lateinit var tinggi: EditText
    lateinit var sisi: EditText
    lateinit var hasil: TextView
    lateinit var btnSegitiga: Button
    lateinit var btnKubus: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pertemuan2)

        // 🔷 TOOLBAR
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarKubus)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Bangun Ruang"

        toolbar.setNavigationOnClickListener {
            finish()
        }

        // 🔷 INIT VIEW
        alas = findViewById(R.id.inputAlas)
        tinggi = findViewById(R.id.inputTinggi)
        sisi = findViewById(R.id.inputSisi)
        hasil = findViewById(R.id.tvHasil)

        btnSegitiga = findViewById(R.id.btnSegitiga)
        btnKubus = findViewById(R.id.btnKubus)

        // 🔷 HITUNG SEGITIGA
        btnSegitiga.setOnClickListener {

            val a = alas.text.toString().toDoubleOrNull()
            val t = tinggi.text.toString().toDoubleOrNull()

            if (a != null && t != null) {
                val luas = 0.5 * a * t
                hasil.text = "Luas Segitiga = $luas"
            } else {
                hasil.text = "Input tidak valid"
            }
        }

        // 🔷 HITUNG KUBUS
        btnKubus.setOnClickListener {

            val s = sisi.text.toString().toDoubleOrNull()

            if (s != null) {
                val volume = s * s * s
                hasil.text = "Volume Kubus = $volume"
            } else {
                hasil.text = "Input tidak valid"
            }
        }

        // 🔷 EDGE TO EDGE
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}