package com.example.naura_orbit.Home.pertemuan_10.kelahiran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naura_orbit.databinding.FragmentTabCKelahiranBinding

class TabCKelahiranFragment : Fragment() {

    private var _binding: FragmentTabCKelahiranBinding? = null
    private val binding get() = _binding!!

    private val listKelahiran = mutableListOf<KelahiranModel>()
    private val listKelahiranFiltered = mutableListOf<KelahiranModel>()
    private lateinit var adapter: KelahiranAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabCKelahiranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (listKelahiran.isEmpty()) {
            listKelahiran.addAll(generateDummyKelahiran())
        }
        listKelahiranFiltered.clear()
        listKelahiranFiltered.addAll(listKelahiran)

        adapter = KelahiranAdapter(listKelahiranFiltered) { kelahiran ->
            com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                .setTitle("Detail Pencatatan Kelahiran")
                .setMessage("Nama Bayi: ${kelahiran.namaBayi}\nTanggal Lahir: ${kelahiran.tanggalLahir}\nJenis Kelamin: ${kelahiran.jenisKelamin}\n\nNama Ayah:\n${kelahiran.namaAyah}\n\nNama Ibu:\n${kelahiran.namaIbu}\n\nBerat Lahir: ${kelahiran.beratLahir}\nPanjang Lahir: ${kelahiran.panjangLahir}")
                .setPositiveButton("Tutup", null)
                .show()
        }

        binding.rvKelahiran.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TabCKelahiranFragment.adapter
        }
    }

    fun performSearch(query: String) {
        listKelahiranFiltered.clear()
        if (query.isEmpty()) {
            listKelahiranFiltered.addAll(listKelahiran)
        } else {
            val queryLower = query.lowercase()
            listKelahiran.forEach {
                if (it.namaBayi.lowercase().contains(queryLower) ||
                    it.namaAyah.lowercase().contains(queryLower) ||
                    it.namaIbu.lowercase().contains(queryLower)) {
                    listKelahiranFiltered.add(it)
                }
            }
        }
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun generateDummyKelahiran(): List<KelahiranModel> {
        val list = mutableListOf<KelahiranModel>()
        val namaBayi = listOf("Aditya Pratama", "Alya Nabila", "Bima Sakti", "Citra Kirana", "Dian Sastro")
        val jk = listOf("Laki-laki", "Perempuan", "Laki-laki", "Perempuan", "Perempuan")
        val ayah = listOf("Budi Pratama", "Hendra Wijaya", "Setiawan", "Rudi Hartono", "Agus Susanto")
        val ibu = listOf("Siti Aminah", "Dewi Lestari", "Rina Wulandari", "Kartika Sari", "Ani Yudhoyono")

        for (i in 1..15) {
            val idx = (i - 1) % 5
            list.add(
                KelahiranModel(
                    namaBayi = "${namaBayi[idx]} Ke-$i",
                    tanggalLahir = "2026-06-${10 + i}",
                    jenisKelamin = jk[idx],
                    namaAyah = ayah[idx],
                    namaIbu = ibu[idx],
                    beratLahir = "${3.0 + (i * 0.1)} Kg",
                    panjangLahir = "${48 + (i % 3)} Cm"
                )
            )
        }
        return list
    }

    fun tambahKelahiranBaru(kelahiran: KelahiranModel) {
        listKelahiran.add(0, kelahiran)
        listKelahiranFiltered.add(0, kelahiran)
        if (::adapter.isInitialized) {
            adapter.notifyItemInserted(0)
            binding.rvKelahiran.scrollToPosition(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
