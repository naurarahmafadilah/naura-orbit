package com.example.naura_orbit.Home.pertemuan_10.kematian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naura_orbit.databinding.FragmentTabCKematianBinding

class TabCKematianFragment : Fragment() {

    private var _binding: FragmentTabCKematianBinding? = null
    private val binding get() = _binding!!

    private val listKematian = mutableListOf<KematianModel>()
    private val listKematianFiltered = mutableListOf<KematianModel>()
    private lateinit var adapter: KematianAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabCKematianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (listKematian.isEmpty()) {
            listKematian.addAll(generateDummyKematian())
        }
        listKematianFiltered.clear()
        listKematianFiltered.addAll(listKematian)

        adapter = KematianAdapter(listKematianFiltered) { kematian ->
            com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                .setTitle("Detail Pencatatan Kematian")
                .setMessage("Nama Jenazah: ${kematian.namaJenazah}\nNo NIK:\n${kematian.noKtp}\n\nTanggal Wafat: ${kematian.tanggalWafat}\nJenis Kelamin: ${kematian.jenisKelamin}\n\nPenyebab Wafat:\n${kematian.penyebabWafat}\n\nTempat Wafat:\n${kematian.tempatWafat}\n\nTempat Pemakaman:\n${kematian.makam}")
                .setPositiveButton("Tutup", null)
                .show()
        }

        binding.rvKematian.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TabCKematianFragment.adapter
        }
    }

    fun performSearch(query: String) {
        listKematianFiltered.clear()
        if (query.isEmpty()) {
            listKematianFiltered.addAll(listKematian)
        } else {
            val queryLower = query.lowercase()
            listKematian.forEach {
                if (it.namaJenazah.lowercase().contains(queryLower) ||
                    it.noKtp.contains(query) ||
                    it.penyebabWafat.lowercase().contains(queryLower)) {
                    listKematianFiltered.add(it)
                }
            }
        }
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun generateDummyKematian(): List<KematianModel> {
        val list = mutableListOf<KematianModel>()
        val nama = listOf("Kartorejo", "Siti Rahayu", "Sumitro", "Wulandari", "Agus Salim")
        val jk = listOf("Laki-laki", "Perempuan", "Laki-laki", "Perempuan", "Laki-laki")
        val penyebab = listOf("Sakit Tua", "Komplikasi Medis", "Serangan Jantung", "Kecelakaan", "Sakit Paru-Paru")
        val makam = listOf("TPU NusaData Selatan", "TPU Kembang Kuning", "TPU NusaData Timur", "TPU Desa Adat", "TPU Islam Karanglo")

        for (i in 1..12) {
            val idx = (i - 1) % 5
            list.add(
                KematianModel(
                    namaJenazah = "${nama[idx]} (Alm) Ke-$i",
                    noKtp = "352239000000${7000 + i}",
                    tanggalWafat = "2026-05-${12 + i}",
                    jenisKelamin = jk[idx],
                    penyebabWafat = penyebab[idx],
                    tempatWafat = "Rumah Duka RT 0$idx",
                    makam = makam[idx]
                )
            )
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
