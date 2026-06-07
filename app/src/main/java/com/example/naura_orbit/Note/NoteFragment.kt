package com.example.naura_orbit.Note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naura_orbit.data.AppDatabase
import com.example.naura_orbit.data.entity.WargaEntity
import com.example.naura_orbit.databinding.FragmentNoteBinding
import kotlinx.coroutines.launch

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NoteAdapter
    private lateinit var db: AppDatabase
    private val wargaList = mutableListOf<WargaEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        // Memasang adapter dengan data list warga terbaru
        adapter = NoteAdapter(wargaList, this)

        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = adapter

        fetchNotes()

        // --- AKSI TOMBOL FAB DENGAN PILIHAN POP-UP DIALOG ---
        binding.fabAddNote.setOnClickListener {
            val pilihanLayanan = arrayOf("Aspirasi & Keluhan Warga", "Pengajuan Kartu Keluarga (KK)")

            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Pilih Jenis Layanan")
                .setItems(pilihanLayanan) { _, posisi ->
                    when (posisi) {
                        0 -> startActivity(Intent(requireContext(), NoteFormActivity::class.java)) // Buka Form Aspirasi
                        1 -> startActivity(Intent(requireContext(), KkFormActivity::class.java))   // Buka Form KK
                    }
                }
                .show()
        }
    }

    // Mengambil data dari tabel warga di Room Database & Mengontrol Tampilan Empty State
    private fun fetchNotes() {
        lifecycleScope.launch {
            val dataWarga = db.wargaDao().getAllWarga()
            wargaList.clear()
            wargaList.addAll(dataWarga)
            adapter.notifyDataSetChanged()

            // --- KONTROL VISIBILITY EMPTY STATE (VERSI 1) ---
            if (wargaList.isEmpty()) {
                binding.layoutEmptyState.visibility = View.VISIBLE // Munculkan pemberitahuan kosong
                binding.rvNotes.visibility = View.GONE             // Sembunyikan daftar RecyclerView
            } else {
                binding.layoutEmptyState.visibility = View.GONE    // Sembunyikan pemberitahuan kosong
                binding.rvNotes.visibility = View.VISIBLE          // Tampilkan daftar RecyclerView
            }
        }
    }

    // Fungsi hapus data warga yang dipanggil dari NoteAdapter
    fun deleteNote(warga: WargaEntity) {
        lifecycleScope.launch {
            db.wargaDao().deleteWarga(warga)
            fetchNotes() // Refresh data otomatis & cek ulang status list kosong
            Toast.makeText(requireContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchNotes() // Otomatis refresh saat kembali dari NoteFormActivity maupun KkFormActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}