package com.example.naura_orbit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Jika kamu tidak menggunakan Navigation Component,
        // pastikan fragment pertama (HomeFragment) sudah muncul lewat XML
    }
}