package com.example.naura_orbit.Home.pertemuan_10.pindah

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PindahTabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabAPindahFragment()
            1 -> TabBPindahFragment()
            2 -> TabCPindahFragment()
            else -> throw IllegalStateException("Posisi tab tidak valid")
        }
    }
}
