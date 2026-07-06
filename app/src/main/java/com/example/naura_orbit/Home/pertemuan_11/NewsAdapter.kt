package com.example.naura_orbit.Home.pertemuan_11.api_news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.naura_orbit.Home.pertemuan_11.NewsModel
import com.example.naura_orbit.databinding.ItemNewsBinding

class NewsAdapter(private val newsList: List<NewsModel>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.binding.tvNewsTitle.text = news.author

        // Set realistic categories and dates for Bina Desa
        var categoryText = ""
        var dateText = ""
        when (position % 3) {
            0 -> {
                categoryText = "INFO DIGITAL"
                dateText = "Baru saja"
            }
            1 -> {
                categoryText = "STATISTIK WARGA"
                dateText = "3 jam yang lalu"
            }
            2 -> {
                categoryText = "BINA DESA"
                dateText = "Kemarin"
            }
        }
        holder.binding.tvNewsCategory.text = categoryText
        holder.binding.tvNewsDate.text = dateText

        // Memuat gambar berita (bisa berupa URL picsum atau path resource lokal)
        Glide.with(holder.itemView.context)
            .load(news.download_url)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .into(holder.binding.imgNews)

        // Aksi Klik Card Berita untuk melihat detail lengkap
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            
            // Inflate custom dialog layout
            val dialogBinding = com.example.naura_orbit.databinding.DialogNewsDetailBinding.inflate(
                LayoutInflater.from(context)
            )
            
            // Bind data to dialog views
            dialogBinding.tvDetailTitle.text = news.author
            dialogBinding.tvDetailContent.text = news.content
            dialogBinding.tvDetailCategory.text = categoryText
            dialogBinding.tvDetailDate.text = "Diposting: $dateText"
            
            // Load news image into dialog
            Glide.with(context)
                .load(news.download_url)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(dialogBinding.imgDetailNews)
            
            // Show the custom popup dialog
            com.google.android.material.dialog.MaterialAlertDialogBuilder(context)
                .setView(dialogBinding.root)
                .setPositiveButton("Tutup") { d, _ -> d.dismiss() }
                .show()
        }
    }

    override fun getItemCount(): Int = newsList.size
}