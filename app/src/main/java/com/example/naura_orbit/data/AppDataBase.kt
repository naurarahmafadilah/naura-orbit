package com.example.naura_orbit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.naura_orbit.data.dao.WargaDao
import com.example.naura_orbit.data.entity.WargaEntity

@Database(
    entities = [WargaEntity::class], // Murni hanya warga
    version = 4, // Naik ke versi 4 agar Room mereset dan membuat tabel warga yang baru
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wargaDao(): WargaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Menghapus database lama jika versi naik
                    .build().also { INSTANCE = it }
            }
        }
    }
}