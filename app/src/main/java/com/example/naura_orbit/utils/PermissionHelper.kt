package com.example.naura_orbit.Home.pertemuan_10.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionHelper {

    /**
     * Memeriksa apakah versi Android perangkat membutuhkan izin runtime untuk notifikasi (Android 13 / API 33+).
     * @return true jika perangkat berjalan di Android 13 ke atas.
     */
    fun isNotificationPermissionRequired(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    /**
     * Memeriksa apakah suatu izin tertentu sudah diberikan oleh pengguna atau belum.
     * @param context Aliran konteks dari Activity/Fragment.
     * @param permission String manifes izin yang ingin dicek (misal: Manifest.permission.POST_NOTIFICATIONS).
     * @return true jika izin sudah disetujui.
     */
    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Memicu sistem pop-up dialog bawaan Android untuk meminta izin kepada pengguna.
     * @param launcher ActivityResultLauncher yang sudah didaftarkan di Activity tujuan.
     * @param permission Jenis izin yang diminta.
     */
    fun requestPermission(launcher: ActivityResultLauncher<String>, permission: String) {
        launcher.launch(permission)
    }
}