package com.example.naura_orbit.pertemuan_3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.naura_orbit.R
import com.example.naura_orbit.databinding.ActivityGreetingBinding

class GreetingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGreetingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGreetingBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_greeting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra("USERNAME")

        binding.tvUser.text = "Halo, $username"
    }
}