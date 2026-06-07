package com.example.naura_orbit.Note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naura_orbit.data.entity.WargaEntity
import com.example.naura_orbit.databinding.ItemWargaNoteBinding // 1. Menggunakan binding baru
import com.example.naura_orbit.Note.NoteFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoteAdapter(
    private val notes: List<WargaEntity>, // Menggunakan WargaEntity
    private val noteFragment: NoteFragment
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // 2. ViewHolder disesuaikan menggunakan ItemWargaNoteBinding
    inner class NoteViewHolder(val binding: ItemWargaNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // 3. Inflate menggunakan ItemWargaNoteBinding sesuai nama file item_warga_note.xml
        val binding = ItemWargaNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]

        // 4. Memetakan properti WargaEntity ke ID komponen yang ada di item_warga_note.xml
        holder.binding.tvWargaName.text = note.nama
        holder.binding.tvWargaContent.text = "NIK: ${note.nik}"
        holder.binding.tvWargaSub.text = "📍 Wilayah RT/RW: ${note.rtRw}"

        // Aksi Klik Tombol Hapus Premium
        holder.binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Hapus Data Warga")
                .setMessage("Apakah Anda yakin ingin menghapus data warga '${note.nama}' ini?")
                .setPositiveButton("Ya, Hapus") { dialog, _ ->

                    // Memanggil fungsi delete di NoteFragment (Pastikan parameter di NoteFragment juga memakai WargaEntity)
                    noteFragment.deleteNote(note)

                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun getItemCount(): Int = notes.size
}