package com.example.naura_orbit.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.naura_orbit.data.entity.WargaEntity

@Dao
interface WargaDao {
    @Query("SELECT * FROM warga")
    suspend fun getAllWarga(): List<WargaEntity>

    @Insert
    suspend fun insertWarga(warga: WargaEntity)

    @Update
    suspend fun updateWarga(warga: WargaEntity)

    @Delete
    suspend fun deleteWarga(warga: WargaEntity)
}