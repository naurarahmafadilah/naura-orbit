package com.example.naura_orbit.Home.pertemuan_4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.naura_orbit.R

class Custom2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_custom2)

        // 🔹 Handle padding (edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 Ambil komponen dari XML
        val tvJudul = findViewById<TextView>(R.id.tvJudul)
        val tvDesc = findViewById<TextView>(R.id.tvDesc)

        // 🔹 Ambil data dari Intent
        val judul = intent.getStringExtra("judul")
        val desc = intent.getStringExtra("desc")

        // 🔹 Tampilkan ke UI
        tvJudul.text = judul
        tvDesc.text = desc


        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }
    }
}