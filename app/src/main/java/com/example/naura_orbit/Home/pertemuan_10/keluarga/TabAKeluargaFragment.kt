package com.example.naura_orbit.Home.pertemuan_10.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naura_orbit.R
import com.example.naura_orbit.databinding.FragmentTabAKeluargaBinding

class TabAKeluargaFragment : Fragment() {
    private var _binding: FragmentTabAKeluargaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabAKeluargaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Di sini kamu bisa melakukan set teks ringkasan / grafik data keluarga jika ada
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}