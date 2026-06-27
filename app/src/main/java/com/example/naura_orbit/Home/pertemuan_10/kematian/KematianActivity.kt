package com.example.naura_orbit.Home.pertemuan_10.kematian

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityKematianBinding
import com.google.android.material.tabs.TabLayoutMediator

class KematianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKematianBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKematianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackKematian.setOnClickListener {
            finish()
        }

        binding.btnAddKematian.setOnClickListener {
            val url = "https://docs.google.com/forms/d/e/1FAIpQLScyv10v-3lT_XgS3tU6h9U-Q9m8xXJvU80F_WJtP6vJvDqUgw/viewform?usp=sf_link"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val adapter = KematianTabAdapter(this)
        binding.viewPagerKematian.adapter = adapter

        TabLayoutMediator(binding.tabLayoutKematian, binding.viewPagerKematian) { tab, position ->
            when (position) {
                0 -> tab.text = "Statistik"
                1 -> tab.text = "Info Layanan"
                2 -> {
                    tab.text = "Daftar Kematian"
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 12
                }
            }
        }.attach()

        binding.etSearchKematian.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                val fragment = supportFragmentManager.findFragmentByTag("f2")
                if (fragment is TabCKematianFragment) {
                    fragment.performSearch(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
