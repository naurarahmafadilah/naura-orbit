package com.example.naura_orbit.Home.pertemuan_10.pindah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naura_orbit.databinding.ItemPindahBinding

class PindahAdapter(
    private val pindahList: List<PindahModel>,
    private val onItemClick: (PindahModel) -> Unit
) : RecyclerView.Adapter<PindahAdapter.PindahViewHolder>() {

    inner class PindahViewHolder(val binding: ItemPindahBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PindahViewHolder {
        val binding = ItemPindahBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PindahViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PindahViewHolder, position: Int) {
        val item = pindahList[position]
        with(holder.binding) {
            tvNamaWarga.text = item.namaWarga
            tvJenisMutasi.text = if (item.jenisMutasi == "Masuk") "Pindah Masuk" else "Pindah Keluar"
            tvTanggalMutasi.text = item.tanggalMutasi
            tvNoKtp.text = item.noKtp
            tvAlamatAsal.text = item.alamatAsal
            tvAlamatTujuan.text = item.alamatTujuan
            tvAlasan.text = item.alasan

            btnDetailPindah.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = pindahList.size
}
