package com.example.naura_orbit.Home.pertemuan_10.kematian

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class KematianTabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabAKematianFragment()
            1 -> TabBKematianFragment()
            2 -> TabCKematianFragment()
            else -> throw IllegalStateException("Posisi tab tidak valid")
        }
    }
}
