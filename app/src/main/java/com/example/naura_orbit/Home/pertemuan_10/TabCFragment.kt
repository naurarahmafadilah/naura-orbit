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

    private val fullListWargaDesa = mutableListOf<WargaModel>()
    private val displayedListWargaDesa = mutableListOf<WargaModel>()
    private lateinit var wargaAdapter: WargaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabCBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Isi list global dengan 50 data awal jika list-nya masih kosong
        if (fullListWargaDesa.isEmpty()) {
            fullListWargaDesa.addAll(generate50Warga())
        }
        displayedListWargaDesa.clear()
        displayedListWargaDesa.addAll(fullListWargaDesa)

        // Inisialisasi adapter dengan data warga yang ditampilkan
        wargaAdapter = WargaAdapter(displayedListWargaDesa) { warga ->
            com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                .setTitle("Detail Informasi Warga")
                .setMessage("Nama: ${warga.nama}\n\nNo KTP:\n${warga.noKtp}\n\nJenis Kelamin: ${warga.jenisKelamin}\nAgama: ${warga.agama}\n\nPekerjaan:\n${warga.pekerjaan}\n\nNo. Telp: ${warga.telp}\nEmail: ${warga.email}")
                .setPositiveButton("Tutup", null)
                .show()
        }

        // Setup RecyclerView dengan format vertikal memanjang ke bawah
        binding.rvWargaProduk.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = wargaAdapter
        }
    }

    // Fungsi publik untuk menerima data warga baru dari WargaActivity
    fun tambahDataWargaBaru(wargaBaru: WargaModel) {
        fullListWargaDesa.add(0, wargaBaru)
        displayedListWargaDesa.add(0, wargaBaru)

        // Beritahu adapter bahwa ada item baru di posisi paling atas
        if (::wargaAdapter.isInitialized) {
            wargaAdapter.notifyItemInserted(0)
            binding.rvWargaProduk.scrollToPosition(0) // Otomatis scroll ke atas
        }
    }

    // Fungsi publik untuk melakukan pencarian warga berdasarkan NIK atau Nama
    fun performSearch(query: String) {
        val cleanQuery = query.trim().lowercase()
        displayedListWargaDesa.clear()
        if (cleanQuery.isEmpty()) {
            displayedListWargaDesa.addAll(fullListWargaDesa)
        } else {
            val filtered = fullListWargaDesa.filter {
                it.nama.lowercase().contains(cleanQuery) ||
                it.noKtp.lowercase().contains(cleanQuery)
            }
            displayedListWargaDesa.addAll(filtered)
        }
        if (::wargaAdapter.isInitialized) {
            wargaAdapter.notifyDataSetChanged()
        }
    }

    // Fungsi helper untuk membuat 50 data tiruan dengan cepat
    private fun generate50Warga(): List<WargaModel> {
        val daftar = mutableListOf<WargaModel>()

        val namaDasar = listOf("Kartika", "Garda Nainggolan S.H.", "Kani Vicky Zulaika")
        val jkDasar = listOf("Laki-laki", "Laki-laki", "Perempuan")
        val pekerjaanDasar = listOf("Pelajar", "Tukang Kayu", "Ibu Rumah Tangga")
        val emailDasar = listOf("oktaviani.laswi", "darsirah.utami", "raden.rahmawati")

        for (i in 1..50) {
            val index = (i - 1) % 3

            val namaUnik = "${namaDasar[index]} Ke-$i"
            val ktpUnik = "352239190808${5000 + i}"
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