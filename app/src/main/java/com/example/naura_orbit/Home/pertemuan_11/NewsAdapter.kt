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
        holder.binding.tvNewsTitle.text = "Berita NusaData: ${news.author}"

        // Memuat gambar berita dari API menggunakan library Glide
        Glide.with(holder.itemView.context)
            .load(news.download_url)
            .into(holder.binding.imgNews)
    }

    override fun getItemCount(): Int = newsList.size
}