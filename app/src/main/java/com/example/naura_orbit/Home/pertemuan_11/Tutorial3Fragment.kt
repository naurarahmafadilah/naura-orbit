package com.example.naura_orbit.Home.pertemuan_11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naura_orbit.databinding.FragmentTutorial3Binding

class Tutorial3Fragment : Fragment() {
    private var _binding: FragmentTutorial3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorial3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 🟢 Semua logic klik dipindah ke AuthActivity, di sini dibiarkan kosong dan aman!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}