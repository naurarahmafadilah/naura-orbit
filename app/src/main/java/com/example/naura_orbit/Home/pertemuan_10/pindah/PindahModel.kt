package com.example.naura_orbit.Home.pertemuan_10.pindah

data class PindahModel(
    val namaWarga: String,
    val noKtp: String,
    val jenisMutasi: String, // "Masuk" atau "Keluar"
    val tanggalMutasi: String,
    val alamatAsal: String,
    val alamatTujuan: String,
    val alasan: String
)
