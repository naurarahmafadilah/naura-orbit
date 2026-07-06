package com.example.naura_orbit.Profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.naura_orbit.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load values
        loadProfileData(view)

        val btnBack: ImageButton = view.findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val btnInstagram: LinearLayout = view.findViewById(R.id.btnInstagram)
        btnInstagram.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            var url = sharedPref.getString("profile_instagram", "@naurarahmafadilah") ?: "@naurarahmafadilah"
            if (!url.startsWith("http")) {
                val cleanUser = url.replace("@", "")
                url = "https://instagram.com/$cleanUser"
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val btnEmail: LinearLayout = view.findViewById(R.id.btnEmail)
        btnEmail.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val email = sharedPref.getString("profile_email", "naura24si@mahasiswa.pcr.ac.id") ?: "naura24si@mahasiswa.pcr.ac.id"
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            try {
                startActivity(emailIntent)
            } catch (e: Exception) {
                Toast.makeText(context, "Tidak ada aplikasi email yang terpasang", Toast.LENGTH_SHORT).show()
            }
        }

        val btnGithub: LinearLayout = view.findViewById(R.id.btnGithub)
        btnGithub.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            var url = sharedPref.getString("profile_github", "github.com/naura-orbit") ?: "github.com/naura-orbit"
            if (!url.startsWith("http")) {
                url = "https://$url"
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val btnSettings: LinearLayout = view.findViewById(R.id.btnSettings)
        btnSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        val btnHelpCenter: LinearLayout = view.findViewById(R.id.btnHelpCenter)
        btnHelpCenter.setOnClickListener {
            startActivity(Intent(requireContext(), HelpCenterActivity::class.java))
        }

        val btnEditProfile: MaterialButton = view.findViewById(R.id.btnEditProfile)
        btnEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
    }

    private fun loadProfileData(view: View) {
        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        val tvProfileName: TextView = view.findViewById(R.id.tvProfileName)
        val tvProfileRole: TextView = view.findViewById(R.id.tvProfileRole)
        val tvAsalSekolah: TextView = view.findViewById(R.id.tvAsalSekolah)
        val tvIdentitas: TextView = view.findViewById(R.id.tvIdentitas)
        val tvAlamat: TextView = view.findViewById(R.id.tvAlamat)
        val tvKodePos: TextView = view.findViewById(R.id.tvKodePos)
        val tvKabupatenKota: TextView = view.findViewById(R.id.tvKabupatenKota)
        val tvProgramStudi: TextView = view.findViewById(R.id.tvProgramStudi)
        val tvTahunMasuk: TextView = view.findViewById(R.id.tvTahunMasuk)
        val tvJenisKelamin: TextView = view.findViewById(R.id.tvJenisKelamin)
        val tvInstagramUser: TextView = view.findViewById(R.id.tvInstagramUser)
        val tvEmailAddress: TextView = view.findViewById(R.id.tvEmailAddress)
        val tvGithubLink: TextView = view.findViewById(R.id.tvGithubLink)

        tvProfileName.text = sharedPref.getString("reg_user", "Naura Rahma")
        tvProfileRole.text = sharedPref.getString("reg_role", "UI/UX Designer • 2457301111")
        tvAsalSekolah.text = sharedPref.getString("profile_asal_sekolah", "Politeknik Caltex Riau")
        tvIdentitas.text = sharedPref.getString("profile_identitas", "Mahasiswa")
        tvAlamat.text = sharedPref.getString("profile_alamat", "Jl. Umban Sari No. 1")
        tvKodePos.text = sharedPref.getString("profile_kode_pos", "28265")
        tvKabupatenKota.text = sharedPref.getString("profile_kabupaten_kota", "Pekanbaru")
        tvProgramStudi.text = sharedPref.getString("profile_program_studi", "Sistem Informasi")
        tvTahunMasuk.text = sharedPref.getString("profile_tahun_masuk", "2024")
        tvJenisKelamin.text = sharedPref.getString("profile_jenis_kelamin", "Perempuan")
        tvInstagramUser.text = sharedPref.getString("profile_instagram", "@naurarahmafadilah")
        tvEmailAddress.text = sharedPref.getString("profile_email", "naura24si@mahasiswa.pcr.ac.id")
        tvGithubLink.text = sharedPref.getString("profile_github", "github.com/naura-orbit")
    }

    override fun onResume() {
        super.onResume()
        view?.let { loadProfileData(it) }
    }
}