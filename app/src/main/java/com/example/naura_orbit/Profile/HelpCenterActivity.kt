package com.example.naura_orbit.Profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.R
import com.google.android.material.button.MaterialButton

class HelpCenterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_center)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.btnContactSupport).setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:naura24si@mahasiswa.pcr.ac.id?subject=NusaData%20Support%20Request")
            }
            try {
                startActivity(emailIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "Tidak ada aplikasi email yang terpasang", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
