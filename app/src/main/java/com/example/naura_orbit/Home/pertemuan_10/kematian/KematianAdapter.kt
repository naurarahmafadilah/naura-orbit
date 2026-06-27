package com.example.naura_orbit.Home.pertemuan_10.kematian

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naura_orbit.databinding.ItemKematianBinding

class KematianAdapter(
    private val kematianList: List<KematianModel>,
    private val onItemClick: (KematianModel) -> Unit
) : RecyclerView.Adapter<KematianAdapter.KematianViewHolder>() {

    inner class KematianViewHolder(val binding: ItemKematianBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KematianViewHolder {
        val binding = ItemKematianBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return KematianViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KematianViewHolder, position: Int) {
        val item = kematianList[position]
        with(holder.binding) {
            tvNamaJenazah.text = item.namaJenazah
            tvJenisKelamin.text = item.jenisKelamin
            tvTanggalWafat.text = item.tanggalWafat
            tvNoKtp.text = item.noKtp
            tvPenyebab.text = item.penyebabWafat
            tvTempatWafat.text = item.tempatWafat
            tvMakam.text = item.makam

            btnDetailKematian.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = kematianList.size
}
