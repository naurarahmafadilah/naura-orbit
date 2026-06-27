package com.example.naura_orbit.Home.pertemuan_10.pindah

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityPindahBinding
import com.google.android.material.tabs.TabLayoutMediator

class PindahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPindahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPindahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackPindah.setOnClickListener {
            finish()
        }

        binding.btnAddPindah.setOnClickListener {
            val url = "https://docs.google.com/forms/d/e/1FAIpQLScyv10v-3lT_XgS3tU6h9U-Q9m8xXJvU80F_WJtP6vJvDqUgw/viewform?usp=sf_link"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val adapter = PindahTabAdapter(this)
        binding.viewPagerPindah.adapter = adapter

        TabLayoutMediator(binding.tabLayoutPindah, binding.viewPagerPindah) { tab, position ->
            when (position) {
                0 -> tab.text = "Statistik"
                1 -> tab.text = "Info Layanan"
                2 -> {
                    tab.text = "Daftar Pindah"
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 18
                }
            }
        }.attach()

        binding.etSearchPindah.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCPindahFragment) {
                    fragment.performSearch(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
