package com.example.naura_orbit.Home.pertemuan_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naura_orbit.databinding.FragmentTabCBinding

class TabCFragment : Fragment() {

    private lateinit var binding: FragmentTabCBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabCBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Generate 50 data warga secara otomatis
        val listWargaDesa = generate50Warga()

        // Inisialisasi adapter dengan data warga dan callback klik tombol detail
        val wargaAdapter = WargaAdapter(listWargaDesa) { warga ->
            Toast.makeText(requireContext(), "Membuka detail data: ${warga.nama}", Toast.LENGTH_SHORT).show()
        }

        // Setup RecyclerView dengan format vertikal memanjang ke bawah
        binding.rvWargaProduk.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = wargaAdapter
        }
    }

    // Fungsi helper untuk membuat 50 data tiruan dengan cepat
    private fun generate50Warga(): List<WargaModel> {
        val daftar = mutableListOf<WargaModel>()

        // Data dasar cetakan yang diambil dari dashboard web NusaData Anda
        val namaDasar = listOf("Kartika", "Garda Nainggolan S.H.", "Kani Vicky Zulaika")
        val jkDasar = listOf("Laki-laki", "Laki-laki", "Perempuan")
        val pekerjaanDasar = listOf("Pelajar", "Tukang Kayu", "Ibu Rumah Tangga")
        val emailDasar = listOf("oktaviani.laswi", "darsirah.utami", "raden.rahmawati")

        for (i in 1..50) {
            // Menentukan indeks dasar (0, 1, atau 2) secara bergantian menggunakan modul (%)
            val index = (i - 1) % 3

            val namaUnik = "${namaDasar[index]} Ke-$i"
            val ktpUnik = "352239190808${5000 + i}" // Angka KTP berubah di bagian belakang
            val jkUnik = jkDasar[index]
            val agamaUnik = "Islam"
            val pekerjaanUnik = pekerjaanDasar[index]
            val telpUnik = "(+62) 812-3456-${7000 + i}"
            val emailUnik = "${emailDasar[index]}$i@example.net"

            daftar.add(
                WargaModel(namaUnik, ktpUnik, jkUnik, agamaUnik, pekerjaanUnik, telpUnik, emailUnik)
            )
        }
        return daftar
    }
}