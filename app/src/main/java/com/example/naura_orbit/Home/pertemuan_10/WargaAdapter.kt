package com.example.naura_orbit.Home.pertemuan_10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naura_orbit.databinding.ItemWargaBinding

class WargaAdapter(
    private val wargaList: List<WargaModel>,
    private val onItemClick: (WargaModel) -> Unit
) : RecyclerView.Adapter<WargaAdapter.WargaViewHolder>() {

    inner class WargaViewHolder(val binding: ItemWargaBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WargaViewHolder {
        val binding = ItemWargaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WargaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WargaViewHolder, position: Int) {
        val item = wargaList[position]
        with(holder.binding) {
            tvNamaWarga.text = item.nama
            tvNoKtp.text = "No KTP: ${item.noKtp}"
            tvJenisKelamin.text = "Jenis Kelamin: ${item.jenisKelamin}"
            tvAgama.text = "Agama: ${item.agama}"
            tvPekerjaan.text = "Pekerjaan: ${item.pekerjaan}"
            tvTelp.text = "Telp: ${item.telp}"
            tvEmail.text = "Email: ${item.email}"

            // Aksi klik pada tombol detail bawaan card
            btnDetailWarga.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = wargaList.size
}