package com.example.naura_orbit.Home.pertemuan_10.pindah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naura_orbit.databinding.FragmentTabCPindahBinding

class TabCPindahFragment : Fragment() {

    private var _binding: FragmentTabCPindahBinding? = null
    private val binding get() = _binding!!

    private val listPindah = mutableListOf<PindahModel>()
    private val listPindahFiltered = mutableListOf<PindahModel>()
    private lateinit var adapter: PindahAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabCPindahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (listPindah.isEmpty()) {
            listPindah.addAll(generateDummyPindah())
        }
        listPindahFiltered.clear()
        listPindahFiltered.addAll(listPindah)

        adapter = PindahAdapter(listPindahFiltered) { pindah ->
            com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                .setTitle("Detail Mutasi Pindah Warga")
                .setMessage("Nama Warga: ${pindah.namaWarga}\nNo NIK:\n${pindah.noKtp}\n\nJenis Mutasi: Pindah ${pindah.jenisMutasi}\nTanggal Mutasi: ${pindah.tanggalMutasi}\n\nAlamat Asal:\n${pindah.alamatAsal}\n\nAlamat Tujuan:\n${pindah.alamatTujuan}\n\nAlasan Mutasi:\n${pindah.alasan}")
                .setPositiveButton("Tutup", null)
                .show()
        }

        binding.rvPindah.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TabCPindahFragment.adapter
        }
    }

    fun performSearch(query: String) {
        listPindahFiltered.clear()
        if (query.isEmpty()) {
            listPindahFiltered.addAll(listPindah)
        } else {
            val queryLower = query.lowercase()
            listPindah.forEach {
                if (it.namaWarga.lowercase().contains(queryLower) ||
                    it.noKtp.contains(query) ||
                    it.jenisMutasi.lowercase().contains(queryLower)) {
                    listPindahFiltered.add(it)
                }
            }
        }
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun generateDummyPindah(): List<PindahModel> {
        val list = mutableListOf<PindahModel>()
        val nama = listOf("Ahmad Fauzi", "Dewi Lestari", "Rizal Hadi", "Lia Novita", "Taufik Hidayat")
        val jenis = listOf("Masuk", "Keluar", "Masuk", "Keluar", "Masuk")
        val asal = listOf("Jakarta Pusat", "NusaData", "Bandung Wetan", "NusaData", "Yogyakarta")
        val tujuan = listOf("NusaData", "Surabaya", "NusaData", "Semarang", "NusaData")
        val alasan = listOf("Pekerjaan", "Pendidikan", "Ikut Keluarga", "Menikah", "Kembali ke Kampung")

        for (i in 1..18) {
            val idx = (i - 1) % 5
            list.add(
                PindahModel(
                    namaWarga = "${nama[idx]} Ke-$i",
                    noKtp = "35223912345600${10 + i}",
                    jenisMutasi = jenis[idx],
                    tanggalMutasi = "2026-06-${10 + i}",
                    alamatAsal = asal[idx],
                    alamatTujuan = tujuan[idx],
                    alasan = alasan[idx]
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
