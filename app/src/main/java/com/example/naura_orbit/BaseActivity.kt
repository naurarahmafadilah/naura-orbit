package com.example.naura_orbit.pertemuan_6

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.naura_orbit.About.AboutFragment
import com.example.naura_orbit.Home.HomeFragment
import com.example.naura_orbit.Profile.ProfileFragment
import com.example.naura_orbit.Note.NoteFragment // 1. Pastikan import fragment note baru ini sudah ditambahkan
import com.example.naura_orbit.R
import com.example.naura_orbit.databinding.ActivityBaseBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Matikan EdgeToEdge agar tidak narik icon ke atas
        // enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        // 2. Inisialisasi View Binding (WAJIB biar nggak error)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Paksa sistem agar tidak kasih padding tambahan (Solusi Anti-Kehimpit)
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavView) { v, insets ->
            v.setPadding(0, 0, 0, 0) // Menghapus padding otomatis sistem
            insets
        }

        // 4. Tampilkan Fragment Home secara default
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // 5. Jalankan Navigasi
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    Toast.makeText(this, "NusaData Home", Toast.LENGTH_SHORT).show()
                    true
                }
                // 2. Tambahkan aksi perpindahan ke NoteFragment di sini
                R.id.note -> {
                    replaceFragment(NoteFragment())
                    Toast.makeText(this, "Catatan NusaData", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_about -> {
                    replaceFragment(AboutFragment())
                    Toast.makeText(this, "Tentang NusaData", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    Toast.makeText(this, "Profil Pengembang", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}