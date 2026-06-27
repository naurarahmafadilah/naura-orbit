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
        when (position % 3) {
            0 -> {
                holder.binding.tvNewsCategory.text = "INFO DIGITAL"
                holder.binding.tvNewsDate.text = "Baru saja"
            }
            1 -> {
                holder.binding.tvNewsCategory.text = "STATISTIK WARGA"
                holder.binding.tvNewsDate.text = "3 jam yang lalu"
            }
            2 -> {
                holder.binding.tvNewsCategory.text = "BINA DESA"
                holder.binding.tvNewsDate.text = "Kemarin"
            }
        }

        // Memuat gambar berita (bisa berupa URL picsum atau path resource lokal)
        Glide.with(holder.itemView.context)
            .load(news.download_url)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .into(holder.binding.imgNews)
    }

    override fun getItemCount(): Int = newsList.size
}