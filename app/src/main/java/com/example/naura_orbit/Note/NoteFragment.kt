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
            val list = db.wargaDao().getAllWarga()
            if (list.isEmpty()) {
                prePopulateNotes()
            } else {
                binding.layoutEmptyState.visibility = View.GONE
                binding.rvNotes.visibility = View.VISIBLE
                val noteAdapter = NoteAdapter(list, this@NoteFragment)
                binding.rvNotes.adapter = noteAdapter
            }
        }
    }

    private fun prePopulateNotes() {
        lifecycleScope.launch {
            val sampleNotes = listOf(
                WargaEntity(nama = "Rahmat Simbolon", nik = "3201234567890001", rtRw = "Pengajuan pembuatan Akta Kelahiran anak pertama yang belum terbit di RT 04/RW 02."),
                WargaEntity(nama = "Siti Aminah", nik = "3201234567890005", rtRw = "Laporan perpindahan alamat domisili secara mandiri ke RT 05/RW 02."),
                WargaEntity(nama = "Ahmad Fauzi", nik = "3201234567890006", rtRw = "Pendaftaran perbaikan data pada Kartu Identitas Anak (KIA) yang salah ketik NIK."),
                WargaEntity(nama = "Bambang Pamungkas", nik = "3201234567890007", rtRw = "Aspirasi permohonan pengadaan fasilitas tempat pembuangan sampah di RT 02/RW 01."),
                WargaEntity(nama = "Dewi Lestari", nik = "3201234567890009", rtRw = "Pengajuan bantuan sosial sembako berkala untuk lansia di RT 04/RW 01.")
            )
            for (note in sampleNotes) {
                db.wargaDao().insertWarga(note)
            }
            // Muat ulang daftar setelah selesai di-seed
            val list = db.wargaDao().getAllWarga()
            binding.layoutEmptyState.visibility = View.GONE
            binding.rvNotes.visibility = View.VISIBLE
            val noteAdapter = NoteAdapter(list, this@NoteFragment)
            binding.rvNotes.adapter = noteAdapter
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

    // Fungsi update data warga yang dipanggil dari NoteAdapter
    fun updateNote(warga: WargaEntity) {
        lifecycleScope.launch {
            db.wargaDao().updateWarga(warga)
            fetchNotes() // Refresh data otomatis & cek ulang status list kosong
            Toast.makeText(requireContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
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