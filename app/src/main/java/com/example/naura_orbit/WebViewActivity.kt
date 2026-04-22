package com.example.naura_orbit

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.naura_orbit.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔹 Binding
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Bina Desa"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 🔹 WebView Setup
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true

        // 🌐 LINK WEBSITE KAMU
        binding.webView.loadUrl("https://naura-kependudukan.alwaysdata.net/")
    }

    // 🔙 Back Toolbar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // 🔙 Back dalam Web
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}