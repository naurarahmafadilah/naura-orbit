package com.example.naura_orbit.Home.pertemuan_11

import retrofit2.http.GET

interface NewsApiService {
    @GET("v2/list?page=1&limit=10")
    suspend fun getTopNews(): List<NewsModel>
}