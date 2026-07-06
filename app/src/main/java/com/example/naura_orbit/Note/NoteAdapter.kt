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

        // Aksi Klik Card untuk Melihat Detail Catatan Lengkap
        holder.itemView.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Detail Catatan Kependudukan")
                .setMessage("Nama Pemohon:\n${note.nama}\n\nNIK:\n${note.nik}\n\nDetail Aspirasi / RT-RW:\n${note.rtRw}")
                .setPositiveButton("Tutup", null)
                .show()
        }

        // Aksi Klik Tombol Edit
        holder.binding.btnEdit.setOnClickListener {
            val context = holder.itemView.context
            val layout = android.widget.LinearLayout(context).apply {
                orientation = android.widget.LinearLayout.VERTICAL
                setPadding(50, 40, 50, 40)
            }
            val lp = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16, 0, 16)
            }

            val etNama = android.widget.EditText(context).apply {
                hint = "Nama Pemohon"
                setText(note.nama)
                layoutParams = lp
            }
            val etNik = android.widget.EditText(context).apply {
                hint = "NIK (16 digit)"
                setText(note.nik)
                layoutParams = lp
                filters = arrayOf(android.text.InputFilter.LengthFilter(16))
            }
            val etDetail = android.widget.EditText(context).apply {
                hint = "Detail Keluhan / RT-RW"
                setText(note.rtRw)
                layoutParams = lp
            }

            layout.addView(etNama)
            layout.addView(etNik)
            layout.addView(etDetail)

            MaterialAlertDialogBuilder(context)
                .setTitle("Edit Data Catatan")
                .setView(layout)
                .setPositiveButton("Simpan") { dialog, _ ->
                    val nama = etNama.text.toString().trim()
                    val nik = etNik.text.toString().trim()
                    val detail = etDetail.text.toString().trim()

                    if (nama.isEmpty() || nik.isEmpty() || detail.isEmpty()) {
                        android.widget.Toast.makeText(context, "Semua field harus diisi!", android.widget.Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    if (nik.length < 16) {
                        android.widget.Toast.makeText(context, "NIK harus 16 digit!", android.widget.Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    val updatedNote = WargaEntity(
                        id = note.id,
                        nama = nama,
                        nik = nik,
                        rtRw = detail
                    )
                    noteFragment.updateNote(updatedNote)
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

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