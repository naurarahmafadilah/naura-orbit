package com.example.naura_orbit.Home.pertemuan_10.kelahiran

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class KelahiranTabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabAKelahiranFragment()
            1 -> TabBKelahiranFragment()
            2 -> TabCKelahiranFragment()
            else -> throw IllegalStateException("Posisi tab tidak valid")
        }
    }
}
