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

    // 1. Ubah list dan adapter menjadi variabel global kelas agar bisa diakses dari luar
    private val listWargaDesa = mutableListOf<WargaModel>()
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

        // 2. Isi list global dengan 50 data awal jika list-nya masih kosong
        if (listWargaDesa.isEmpty()) {
            listWargaDesa.addAll(generate50Warga())
        }

        // Inisialisasi adapter dengan data warga dan callback klik tombol detail
        wargaAdapter = WargaAdapter(listWargaDesa) { warga ->
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

    // 3. Fungsi publik untuk menerima data warga baru dari WargaActivity
    fun tambahDataWargaBaru(wargaBaru: WargaModel) {
        // Tambahkan ke baris paling atas (indeks 0) agar user langsung melihat data yang baru masuk
        listWargaDesa.add(0, wargaBaru)

        // Beritahu adapter bahwa ada item baru di posisi paling atas
        if (::wargaAdapter.isInitialized) {
            wargaAdapter.notifyItemInserted(0)
            binding.rvWargaProduk.scrollToPosition(0) // Otomatis scroll ke atas
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