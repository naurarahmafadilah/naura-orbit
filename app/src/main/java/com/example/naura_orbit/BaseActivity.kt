package com.example.naura_orbit.pertemuan_6

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.naura_orbit.Home.pertemuan_6.DuaFragment
import com.example.naura_orbit.Home.pertemuan_6.SatuFragment
import com.example.naura_orbit.Home.pertemuan_6.TigaFragment
import com.example.naura_orbit.R
import com.example.naura_orbit.databinding.ActivityBaseBinding


class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Aktifkan fitur Edge-to-Edge
        enableEdgeToEdge()

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Penyesuaian Padding agar Navigasi Bawah nempel sempurna (Instruksi Gambar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Bagian bawah diatur ke 0 sesuai instruksi "After" di gambar kamu
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Tampilkan Fragment Home sebagai halaman pertama
        if (savedInstanceState == null) {
            replaceFragment(SatuFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}