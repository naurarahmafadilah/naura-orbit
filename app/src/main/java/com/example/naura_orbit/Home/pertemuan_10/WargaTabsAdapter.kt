package com.example.naura_orbit.Home.pertemuan_10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WargaTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Menentukan jumlah total tab yang tersedia
    override fun getItemCount(): Int = 3

    // Mengarahkan fragment posisi terkait ke tab yang bersangkutan
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabAFragment() // Fragment Ringkasan data
            1 -> TabBFragment() // Fragment Kontak internal desa
            2 -> TabCFragment() // Fragment utama berisi RecyclerView daftar warga/produk
            else -> throw IllegalStateException("Posisi tab tidak valid")
        }
    }
}