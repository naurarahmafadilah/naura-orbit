package com.example.naura_orbit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "warga")
data class WargaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,      // Nama warga yang meminta bantuan
    val nik: String,       // NIK warga untuk verifikasi
    val rtRw: String       // Detail Permintaan / Keluhan Warga (kita simpan di sini)
)