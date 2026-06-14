package com.example.naura_orbit.Home.pertemuan_10.utils

import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.naura_orbit.Home.pertemuan_10.WargaActivity

class ReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Ambil data kiriman parameter title dan message dari WorkManager
        val title = inputData.getString("KEY_TITLE") ?: "NusaData Pengingat"
        val message = inputData.getString("KEY_MESSAGE") ?: "Waktunya validasi berkas."

        // Ketika notifikasi pengingat diklik, arahkan user kembali ke halaman utama WargaActivity
        val intent = Intent(applicationContext, WargaActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Tembakkan notifikasi lokal ke layar
        NotificationHelper.showNotification(applicationContext, title, message, intent)

        return Result.success()
    }
}