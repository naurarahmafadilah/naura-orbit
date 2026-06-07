package com.example.naura_orbit

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.naura_orbit.Home.pertemuan_3.LoginActivity
// 🟢 Import fragment tutorial buatanmu di sini (sesuaikan foldernya jika beda)
import com.example.naura_orbit.Home.pertemuan_11.Tutorial1Fragment
import com.example.naura_orbit.Home.pertemuan_11.Tutorial2Fragment
import com.example.naura_orbit.Home.pertemuan_11.Tutorial3Fragment
import com.example.naura_orbit.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. PANGGIL FRAGMENT TUTORIAL YANG SUDAH KAMU BUAT
        val fragments = arrayListOf<Fragment>(
            Tutorial1Fragment(), // Fragment slide 1 milikmu
            Tutorial2Fragment(), // Fragment slide 2 milikmu
            Tutorial3Fragment()  // Fragment slide 3 milikmu
        )

        // 2. Pasang Adapter ke ViewPager2
        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment = fragments[position]
        }
        binding.viewPager.adapter = adapter

        // 3. Logika Memunculkan Tombol "Ayo Mulai" di Slide Terakhir (Index 2)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // Update teks indikator titik bulat biasa
                when (position) {
                    0 -> binding.tvIndicator.text = "● ○ ○"
                    1 -> binding.tvIndicator.text = "○ ● ○"
                    2 -> binding.tvIndicator.text = "○ ○ ●"
                }

                // Kuncinya di sini: jika posisi = 2 (artinya slide ke-3)
                if (position == 2) {
                    binding.btnAyoMulai.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.GONE
                    binding.tvIndicator.visibility = View.GONE
                } else {
                    binding.btnAyoMulai.visibility = View.GONE
                    binding.btnNext.visibility = View.VISIBLE
                    binding.tvIndicator.visibility = View.VISIBLE
                }
            }
        })

        // 4. Tombol Lanjut untuk menggeser slide
        binding.btnNext.setOnClickListener {
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }

        // 5. Tombol Ayo Mulai klik untuk ke halaman Login
        binding.btnAyoMulai.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Menutup AuthActivity agar tidak bisa di-back
        }
    }
}