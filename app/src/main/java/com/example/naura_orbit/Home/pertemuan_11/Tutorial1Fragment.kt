package com.example.naura_orbit.Home.pertemuan_11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naura_orbit.databinding.FragmentTutorial1Binding

class Tutorial1Fragment : Fragment() {
    private var _binding: FragmentTutorial1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorial1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Di sini bisa ditambahkan logika spesifik untuk slide 1 jika diperlukan nantinya
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}