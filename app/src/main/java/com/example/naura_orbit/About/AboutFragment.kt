package com.example.naura_orbit.About

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton // Penting: Jangan lupakan import ini
import com.example.naura_orbit.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Memanggil layout fragment_about yang sudah berisi konten lengkap Bina Desa
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi tombol kembali dari layout
        val btnBack: ImageButton = view.findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            // Menggunakan popBackStack untuk kembali ke fragment sebelumnya (Home/Profile)
            parentFragmentManager.popBackStack()
        }
    }
}