package com.example.naura_orbit.Home.pertemuan_10.keluarga

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
// 🟢 PENTING: Mengarah ke ItemAnggotaKeluargaBinding sesuai nama file XML kamu
import com.example.naura_orbit.databinding.ItemAnggotaKeluargaBinding

class KeluargaAdapter(
    private val listKeluarga: List<KeluargaModel>,
    private val onItemClick: (KeluargaModel) -> Unit
) : RecyclerView.Adapter<KeluargaAdapter.KeluargaViewHolder>() {

    inner class KeluargaViewHolder(private val binding: ItemAnggotaKeluargaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(keluarga: KeluargaModel) {
            // Jika di dalam item_anggota_keluarga.xml kamu menggunakan id yang berbeda,
            // sesuaikan nama id setelah kata "binding." berikut ini:
            binding.tvItemNoKK.text = "KK: ${keluarga.noKK}"
            binding.tvItemKepalaKeluarga.text = keluarga.kepalaKeluarga
            binding.tvItemAlamatKeluarga.text = keluarga.alamat
            binding.tvItemJumlahAnggota.text = "${keluarga.jumlahAnggota} Anggota"

            binding.btnDetailKeluarga.setOnClickListener {
                onItemClick(keluarga)
            }

            binding.root.setOnClickListener {
                onItemClick(keluarga)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeluargaViewHolder {
        // 🟢 Menggunakan binding yang pas dengan item_anggota_keluarga.xml
        val binding = ItemAnggotaKeluargaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return KeluargaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeluargaViewHolder, position: Int) {
        holder.bind(listKeluarga[position])
    }

    override fun getItemCount(): Int = listKeluarga.size
}