package com.example.naura_orbit.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naura_orbit.Home.pertemuan_3.LoginActivity
import com.example.naura_orbit.Home.pertemuan_4.BangunRuangActivity
import com.example.naura_orbit.Home.pertemuan_4.Custom2Activity
import com.example.naura_orbit.WebViewActivity
import com.example.naura_orbit.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    // 🔹 Inisialisasi View Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Memasang layout menggunakan View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Logika Klik Button Bangun
        binding.cardBangun.setOnClickListener {
            startActivity(Intent(requireContext(), BangunRuangActivity::class.java))
        }

        // 🌐 Logika Klik Button Bina Desa (WebView)
        binding.cardBinaDesa.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        // 🔹 Logika Klik Button Custom 2
        binding.cardCustom2.setOnClickListener {
            startActivity(Intent(requireContext(), Custom2Activity::class.java))
        }

        // 🔴 Logika Klik Logout
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
            .setPositiveButton("Ya") { _, _ ->
                // 🔥 Hapus Session SharedPreferences
                val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                sharedPref.edit().clear().apply()

                // Pindah ke Login dan hapus tumpukan activity (Backstack)
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
                Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 🔹 Mencegah memory leak
    }
}