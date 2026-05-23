package com.example.naura_orbit.Home.pertemuan_10.keluarga

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class KeluargaTabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Menentukan jumlah total tab yang tersedia (Sama seperti warga)
    override fun getItemCount(): Int = 3

    // Mengarahkan fragment posisi terkait ke tab yang bersangkutan khusus Keluarga
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabAKeluargaFragment() // Fragment Ringkasan data statistik Keluarga
            1 -> TabBKeluargaFragment() // Fragment Kontak / Informasi internal layanan KK
            2 -> TabCKeluargaFragment() // Fragment utama berisi RecyclerView daftar Kartu Keluarga
            else -> throw IllegalStateException("Posisi tab tidak valid")
        }
    }
}