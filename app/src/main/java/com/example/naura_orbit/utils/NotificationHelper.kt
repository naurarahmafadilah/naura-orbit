package com.example.naura_orbit.Home.pertemuan_10.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object NotificationHelper {

    private const val CHANNEL_ID = "nusadata_notification_channel"
    private const val CHANNEL_NAME = "NusaData Kependudukan"
    private const val CHANNEL_DESC = "Notifikasi pendaftaran dan validasi berkas warga NusaData"
    private const val NOTIFICATION_ID = 1001
    private const val REMINDER_ID = 1002

    /**
     * Membuat Notification Channel (Wajib untuk Android 8.0 / API 26 ke atas)
     */
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESC
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Menampilkan Local Notification secara Instan ke layar HP
     */
    fun showNotification(context: Context, title: String, message: String, intent: Intent? = null) {
        createNotificationChannel(context)

        // Set up action klik pada notifikasi
        val pendingIntent = if (intent != null) {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            null
        }

        // Bangun arsitektur tampilan notifikasi formal
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Bisa diganti dengan drawable logo ikon aplikasimu sendiri
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message)) // Mendukung teks panjang agar tidak terpotong

        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent)
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Gunakan ID berbeda (misal berbasis waktu mili) jika ingin notifikasi tidak menimpa satu sama lain
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    /**
     * Menjadwalkan pengingat otomatis di latar belakang menggunakan WorkManager (Reminder Fitur Menit)
     */
    fun scheduleReminder(context: Context, minutes: Long, title: String, message: String) {
        // Bungkus data teks untuk dikirimkan ke kelas ReminderWorker saat jam alarm berbunyi
        val inputData = Data.Builder()
            .putString("KEY_TITLE", title)
            .putString("putString", message) // Menyimpan pesan detail reminder
            .build()

        // Buat permintaan eksekusi satu kali (OneTimeWorkRequest) dengan delay sesuai menit inputan user
        val reminderRequest = OneTimeWorkRequest.Builder(ReminderWorker::class.java)
            .setInitialDelay(minutes, TimeUnit.MINUTES)
            .setInputData(inputData)
            .build()

        // Masukkan antrean ke dalam sistem WorkManager Android
        WorkManager.getInstance(context.applicationContext).enqueue(reminderRequest)
    }
}