package com.example.naura_orbit.Home.pertemuan_10.kelahiran

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naura_orbit.databinding.ItemKelahiranBinding

class KelahiranAdapter(
    private val kelahiranList: List<KelahiranModel>,
    private val onItemClick: (KelahiranModel) -> Unit
) : RecyclerView.Adapter<KelahiranAdapter.KelahiranViewHolder>() {

    inner class KelahiranViewHolder(val binding: ItemKelahiranBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KelahiranViewHolder {
        val binding = ItemKelahiranBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return KelahiranViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KelahiranViewHolder, position: Int) {
        val item = kelahiranList[position]
        with(holder.binding) {
            tvNamaBayi.text = item.namaBayi
            tvJenisKelamin.text = item.jenisKelamin
            tvTanggalLahir.text = item.tanggalLahir
            tvNamaAyah.text = item.namaAyah
            tvNamaIbu.text = item.namaIbu
            tvBeratLahir.text = item.beratLahir
            tvPanjangLahir.text = item.panjangLahir

            btnDetailKelahiran.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = kelahiranList.size
}
